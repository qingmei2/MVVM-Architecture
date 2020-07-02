package com.qingmei2.sample.ui.main.repos

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import androidx.room.withTransaction
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.base.Results
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.utils.processApiResponse
import javax.inject.Inject

@SuppressLint("CheckResult")
class ReposRepository @Inject constructor(
        remote: RemoteReposDataSource,
        local: LocalReposDataSource
) : BaseRepositoryBoth<RemoteReposDataSource, LocalReposDataSource>(remote, local) {

    @MainThread
    suspend fun fetchRepoByPage(
            sort: String,
            pageIndex: Int,
            remoteRequestPerPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Results<List<Repo>> {
        val username: String = UserManager.INSTANCE.login
        return remoteDataSource.queryRepos(username, pageIndex, remoteRequestPerPage, sort)
    }

    @MainThread
    fun fetchRepoDataSourceFactory(): DataSource.Factory<Int, Repo> {
        return localDataSource.fetchRepoDataSourceFactory()
    }

    @WorkerThread
    suspend fun clearAndInsertNewData(items: List<Repo>) {
        localDataSource.clearOldAndInsertNewData(items)
    }

    @WorkerThread
    suspend fun insertNewPageData(items: List<Repo>) {
        localDataSource.insertNewPageData(items)
    }
}

class RemoteReposDataSource @Inject constructor(private val serviceManager: ServiceManager) : IRemoteDataSource {

    suspend fun queryRepos(
            username: String,
            pageIndex: Int,
            perPage: Int,
            sort: String
    ): Results<List<Repo>> {
        return fetchReposByPageInternal(username, pageIndex, perPage, sort)
    }

    private suspend fun fetchReposByPageInternal(
            username: String,
            pageIndex: Int,
            perPage: Int,
            sort: String
    ): Results<List<Repo>> {
        return processApiResponse {
            serviceManager.userService.queryRepos(username, pageIndex, perPage, sort)
        }
    }
}

class LocalReposDataSource @Inject constructor(
        private val db: UserDatabase
) : ILocalDataSource {

    @AnyThread
    fun fetchRepoDataSourceFactory(): DataSource.Factory<Int, Repo> {
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
    private suspend fun insertDataInternal(newPage: List<Repo>) {
        val start = db.userReposDao().getNextIndexInRepos() ?: 0
        val items = newPage.mapIndexed { index, child ->
            child.indexInSortResponse = start + index
            child
        }
        db.userReposDao().insert(items)
    }
}
