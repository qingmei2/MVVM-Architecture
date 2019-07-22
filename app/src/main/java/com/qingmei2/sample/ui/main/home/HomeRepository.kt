package com.qingmei2.sample.ui.main.home

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
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.AsyncProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.processors.UnicastProcessor

@SuppressLint("CheckResult")
class HomeRepository(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource,
        val mAutoDisposeObserver: AsyncProcessor<Unit> = AsyncProcessor.create()
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(remoteDataSource, localDataSource) {

    private val mRemoteRequestStateProcessor: PublishProcessor<Result<List<ReceivedEvent>>> =
            PublishProcessor.create()

    fun subscribeRemoteRequestState(): Flowable<Result<List<ReceivedEvent>>> {
        // hide() ensure subject's security.
        return mRemoteRequestStateProcessor.hide()
    }

    /**
     * refresh home page list, and update local repository(db) after success.
     */
    fun refreshPagedList() {
        fetchEventByPage(1)
                .takeUntil(mAutoDisposeObserver)
                .subscribe { mRemoteRequestStateProcessor.onNext(it) }
    }

    /**
     * initial page list.
     *
     * ensure this function be call one time only.
     */
    fun initPagedListFromDb(): Flowable<PagedList<ReceivedEvent>> {
        return localDataSource.fetchPagedListFromDb(
                boundaryCallback = object : PagedList.BoundaryCallback<ReceivedEvent>() {
                    override fun onZeroItemsLoaded() {
                        fetchEventByPage(1)
                                .takeUntil(mAutoDisposeObserver)
                                .subscribe { mRemoteRequestStateProcessor.onNext(it) }
                    }

                    override fun onItemAtEndLoaded(itemAtEnd: ReceivedEvent) {
                        val currentPageIndex = (itemAtEnd.indexInResponse / 30) + 1
                        val nextPageIndex = currentPageIndex + 1

                        this@HomeRepository.fetchEventByPage(nextPageIndex)
                                .takeUntil(mAutoDisposeObserver)
                                .subscribe { mRemoteRequestStateProcessor.onNext(it) }
                    }
                }
        )
    }

    private fun fetchEventByPage(
            pageIndex: Int,
            remoteRequestPerPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Flowable<Result<List<ReceivedEvent>>> {
        val username: String = UserManager.INSTANCE.login
        return when (pageIndex == 1) {
            true -> remoteDataSource
                    .fetchEventsByPage(username, 1, remoteRequestPerPage)
                    .flatMap { result ->
                        when (result is Result.Success) {
                            true -> localDataSource.clearOldAndInsertNewData(result.data)
                                    .andThen(Flowable.just(result))
                            else -> Flowable.just(result)
                        }
                    }
            false -> remoteDataSource
                    .fetchEventsByPage(username, pageIndex, remoteRequestPerPage)
                    .flatMap { result ->
                        when (result is Result.Success) {
                            true -> localDataSource.insertNewPagedEventData(result.data)
                                    .andThen(Flowable.just(result))
                            else -> Flowable.just(result)
                        }
                    }
        }
    }
}

class HomeRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {

    fun fetchEventsByPage(
            username: String,
            pageIndex: Int,
            perPage: Int
    ): Flowable<Result<List<ReceivedEvent>>> {
        return when (pageIndex) {
            1 -> fetchEventsByPageInternal(username, pageIndex, perPage)
                    .map { Result.success(it) }
                    .onErrorReturn { Result.failure(it) }
                    .startWith(Result.loading())   // step 2.show loading indicator
                    .startWith(Result.idle())      // step 1.reset loading indicator state
            else -> {
                fetchEventsByPageInternal(username, pageIndex, perPage)
                        .map { Result.success(it) }
                        .onErrorReturn { Result.failure(it) }
            }
        }
    }

    private fun fetchEventsByPageInternal(
            username: String,
            pageIndex: Int,
            perPage: Int
    ): Flowable<List<ReceivedEvent>> {
        return serviceManager.userService
                .queryReceivedEvents(username, pageIndex, perPage)
                .observeOn(RxSchedulers.ui)
                .compose(globalHandleError())
                .observeOn(RxSchedulers.io)
                .subscribeOn(RxSchedulers.io)
    }
}

class HomeLocalDataSource(private val db: UserDatabase) : ILocalDataSource {

    fun fetchPagedListFromDb(
            boundaryCallback: PagedList.BoundaryCallback<ReceivedEvent>
    ): Flowable<PagedList<ReceivedEvent>> {
        return db.userReceivedEventDao().queryEvents()
                .toRxPagedList(
                        boundaryCallback = boundaryCallback,
                        fetchSchedulers = RxSchedulers.io
                )
    }

    fun clearOldAndInsertNewData(newPage: List<ReceivedEvent>): Completable {
        return Completable.fromAction {
            db.runInTransaction {
                db.userReceivedEventDao().clearReceivedEvents()
                insertDataInternal(newPage)
            }
        }
    }

    fun insertNewPagedEventData(newPage: List<ReceivedEvent>): Completable {
        return Completable.fromAction {
            db.runInTransaction { insertDataInternal(newPage) }
        }
    }

    private fun insertDataInternal(newPage: List<ReceivedEvent>) {
        val start = db.userReceivedEventDao().getNextIndexInReceivedEvents()
        val items = newPage.mapIndexed { index, child ->
            child.indexInResponse = start + index
            child
        }
        db.userReceivedEventDao().insert(items)
    }
}