package com.qingmei2.sample.ui.main.repos

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.paging.*
import androidx.room.withTransaction
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.architecture.core.ext.paging.globalPagingConfig
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.utils.toast
import javax.inject.Inject

@SuppressLint("CheckResult")
class ReposRepository @Inject constructor(
        remote: RemoteReposDataSource,
        local: LocalReposDataSource
) : BaseRepositoryBoth<RemoteReposDataSource, LocalReposDataSource>(remote, local) {

    var sortKeyProvider: () -> String = { ReposViewModel.sortByUpdate }

    @MainThread
    fun fetchRepoPager(): Pager<Int, Repo> {
        val username: String = UserManager.INSTANCE.login
        val remoteMediator = RepoPageRemoteMediator(remoteDataSource, localDataSource, sortKeyProvider, username)

        return Pager(
                config = globalPagingConfig,
                remoteMediator = remoteMediator,
                pagingSourceFactory = { localDataSource.fetchRepoPagingSource() }
        )
    }
}

class RemoteReposDataSource @Inject constructor(private val serviceManager: ServiceManager) : IRemoteDataSource {

    suspend fun queryRepos(
            username: String,
            pageIndex: Int,
            perPage: Int,
            sort: String
    ): List<Repo> {
        return serviceManager.userService.queryRepos(username, pageIndex, perPage, sort)
    }
}

class LocalReposDataSource @Inject constructor(
        private val db: UserDatabase
) : ILocalDataSource {

    @AnyThread
    fun fetchRepoPagingSource(): PagingSource<Int, Repo> {
        return db.userReposDao().queryRepos()
    }

    @AnyThread
    suspend fun clearOldAndInsertNewData(newPage: List<Repo>) {
        db.withTransaction {
            db.userReposDao().deleteAllRepos()
            insertDataInternal(newPage)
        }
    }

    @AnyThread
    suspend fun insertNewPageData(newPage: List<Repo>) {
        db.withTransaction { insertDataInternal(newPage) }
    }

    @AnyThread
    suspend fun fetchNextIndexInRepos(): Int {
        return db.withTransaction {
            db.userReposDao().getNextIndexInRepos() ?: 0
        }
    }

    @AnyThread
    private suspend fun insertDataInternal(newPage: List<Repo>) {
        val start = fetchNextIndexInRepos()
        val items = newPage.mapIndexed { index, child ->
            child.indexInSortResponse = start + index
            child
        }
        db.userReposDao().insert(items)
    }
}

@OptIn(ExperimentalPagingApi::class)
class RepoPageRemoteMediator(
        private val remoteDataSource: RemoteReposDataSource,
        private val localDataSource: LocalReposDataSource,
        private val sortKeyProvider: () -> String,
        private val username: String
) : RemoteMediator<Int, Repo>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Repo>): MediatorResult {
        return try {
            val pageIndex = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val nextIndex = localDataSource.fetchNextIndexInRepos()
                    if (nextIndex % PAGING_REMOTE_PAGE_SIZE != 0) {
                        return MediatorResult.Success(true)
                    }
                    nextIndex / PAGING_REMOTE_PAGE_SIZE + 1
                }
            }
            val sortKey = sortKeyProvider()
            val data = remoteDataSource.queryRepos(username, pageIndex, PAGING_REMOTE_PAGE_SIZE, sortKey)
            if (loadType == LoadType.REFRESH) {
                localDataSource.clearOldAndInsertNewData(data)
            } else {
                localDataSource.insertNewPageData(data)
            }
            MediatorResult.Success(data.isEmpty())
        } catch (exception: Exception) {
            toast(exception.toString())
            MediatorResult.Error(exception)
        }
    }
}
