package com.qingmei2.sample.ui.main.repos

import android.annotation.SuppressLint
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.paging.DataSource
import com.qingmei2.rhine.base.repository.BaseRepositoryBoth
import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Flowable

@SuppressLint("CheckResult")
class ReposRepository(
        remote: RemoteReposDataSource,
        local: LocalReposDataSource
) : BaseRepositoryBoth<RemoteReposDataSource, LocalReposDataSource>(remote, local) {

    @MainThread
    fun fetchRepoByPage(
            sort: String,
            pageIndex: Int,
            remoteRequestPerPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Flowable<Result<List<Repo>>> {
        val username: String = UserManager.INSTANCE.login
        return remoteDataSource
                .queryRepos(username, pageIndex, remoteRequestPerPage, sort)
    }

    @MainThread
    fun fetchRepoDataSourceFactory(): DataSource.Factory<Int, Repo> {
        return localDataSource.fetchRepoDataSourceFactory()
    }

    @WorkerThread
    fun clearAndInsertNewData(items: List<Repo>) {
        localDataSource.clearOldAndInsertNewData(items)
    }

    @WorkerThread
    fun insertNewPageData(items: List<Repo>) {
        localDataSource.insertNewPageData(items)
    }
}

class RemoteReposDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {

    fun queryRepos(
            username: String,
            pageIndex: Int,
            perPage: Int,
            sort: String
    ): Flowable<Result<List<Repo>>> {
        return when (pageIndex) {
            1 -> fetchReposByPageInternal(username, pageIndex, perPage, sort)
                    .map { Result.success(it) }
                    .onErrorReturn { Result.failure(it) }
            else -> {
                fetchReposByPageInternal(username, pageIndex, perPage, sort)
                        .map { Result.success(it) }
                        .onErrorReturn { Result.failure(it) }
            }
        }
    }

    private fun fetchReposByPageInternal(
            username: String,
            pageIndex: Int,
            perPage: Int,
            sort: String
    ): Flowable<List<Repo>> {
        return serviceManager.userService
                .queryRepos(username, pageIndex, perPage, sort)
                .observeOn(RxSchedulers.ui)
                .compose(globalHandleError())
                .observeOn(RxSchedulers.io)
                .subscribeOn(RxSchedulers.io)
    }
}

class LocalReposDataSource(
        private val db: UserDatabase
) : ILocalDataSource {

    fun fetchRepoDataSourceFactory(): DataSource.Factory<Int, Repo> {
        return db.userReposDao().queryRepos()
    }

    @WorkerThread
    fun clearOldAndInsertNewData(newPage: List<Repo>) {
        db.runInTransaction {
            db.userReposDao().deleteAllRepos()
            insertDataInternal(newPage)
        }
    }

    @WorkerThread
    fun insertNewPageData(newPage: List<Repo>) {
        db.runInTransaction { insertDataInternal(newPage) }
    }

    private fun insertDataInternal(newPage: List<Repo>) {
        val start = db.userReposDao().getNextIndexInRepos()
        val items = newPage.mapIndexed { index, child ->
            child.indexInSortResponse = start + index
            child
        }
        db.userReposDao().insert(items)
    }
}