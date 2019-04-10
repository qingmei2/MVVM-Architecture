package com.qingmei2.sample.ui.main.repos

import android.annotation.SuppressLint
import androidx.paging.PagedList
import com.qingmei2.rhine.base.repository.BaseRepositoryBoth
import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.ext.paging.toRxPagedList
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

@SuppressLint("CheckResult")
class ReposRepository(
        remote: RemoteReposDataSource,
        local: LocalReposDataSource
) : BaseRepositoryBoth<RemoteReposDataSource, LocalReposDataSource>(remote, local) {

    private val mRemoteRequestStateProcessor: PublishProcessor<Result<List<Repo>>> =
            PublishProcessor.create()

    fun subscribeRemoteRequestState(): Flowable<Result<List<Repo>>> {
        return mRemoteRequestStateProcessor
    }

    fun refreshDataSource(sort: String) {
        this.fetchRepoByPage(sort, 1).subscribe { mRemoteRequestStateProcessor.onNext(it) }
    }

    fun fetchPagedListFromDb(sort: String): Flowable<PagedList<Repo>> {
        return localDataSource.fetchPagedListFromDb(
                boundaryCallback = object : PagedList.BoundaryCallback<Repo>() {
                    override fun onZeroItemsLoaded() {
                        refreshDataSource(sort)
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: Repo) {
                        val currentPageIndex = (itemAtEnd.indexInSortResponse / 30) + 1
                        val nextPageIndex = currentPageIndex + 1
                        this@ReposRepository.fetchRepoByPage(sort, nextPageIndex)
                                .subscribe { mRemoteRequestStateProcessor.onNext(it) }
                    }
                }
        )
    }

    private fun fetchRepoByPage(
            sort: String,
            pageIndex: Int,
            remoteRequestPerPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Flowable<Result<List<Repo>>> {
        val username: String = UserManager.INSTANCE.login
        return when (pageIndex == 1) {
            true -> remoteDataSource
                    .queryRepos(username, 1, remoteRequestPerPage, sort)
                    .flatMap { result ->
                        when (result is Result.Success) {
                            true -> localDataSource.clearOldAndInsertNewData(result.data)
                                    .andThen(Flowable.just(result))
                            else -> Flowable.just(result)
                        }
                    }
            false -> {
                remoteDataSource
                        .queryRepos(username, pageIndex, remoteRequestPerPage, sort)
                        .flatMap { result ->
                            when (result is Result.Success) {
                                true -> localDataSource.insertNewPageData(result.data)
                                        .andThen(Flowable.just(result))
                                else -> Flowable.just(result)
                            }
                        }
            }
        }
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
                    .startWith(Result.loading())   // step 2.show loading indicator
                    .startWith(Result.idle())      // step 1.reset loading indicator state
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

    fun fetchPagedListFromDb(
            boundaryCallback: PagedList.BoundaryCallback<Repo>
    ): Flowable<PagedList<Repo>> {
        return db.userReposDao().queryRepos()
                .toRxPagedList(
                        boundaryCallback = boundaryCallback,
                        fetchSchedulers = RxSchedulers.io
                )
    }

    fun clearOldAndInsertNewData(newPage: List<Repo>): Completable {
        db.runInTransaction {
            db.userReposDao().deleteAllRepos()
        }
        return insertNewPageData(newPage)
    }

    fun insertNewPageData(newPage: List<Repo>): Completable {
        return Completable
                .fromAction { insertDataInternal(newPage) }
    }

    private fun insertDataInternal(newPage: List<Repo>) {
        db.runInTransaction {
            val start = db.userReposDao().getNextIndexInRepos()
            val items = newPage.mapIndexed { index, child ->
                child.indexInSortResponse = start + index
                child
            }
            db.userReposDao().insert(items)
        }
    }
}