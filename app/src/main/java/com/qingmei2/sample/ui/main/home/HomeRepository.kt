package com.qingmei2.sample.ui.main.home

import androidx.paging.PagedList
import com.qingmei2.rhine.base.repository.BaseRepositoryBoth
import com.qingmei2.rhine.base.repository.ILocalDataSource
import com.qingmei2.rhine.base.repository.IRemoteDataSource
import com.qingmei2.rhine.ext.paging.toRxPagedList
import com.qingmei2.rhine.util.RxSchedulers
import com.qingmei2.sample.base.Result
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.DISPLAY_EVENT_TYPES
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer

class HomeRepository(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(remoteDataSource, localDataSource) {

    fun fetchPagedListFromDb(): Flowable<PagedList<ReceivedEvent>> {
        return localDataSource.fetchPagedListFromDb()
    }

    fun queryReceivedEventsFromRemote(
            username: String,
            pageIndex: Int,
            perPage: Int = 15
    ): Flowable<Result<List<ReceivedEvent>>> {
        val isRefresh = pageIndex == 1
        return when (isRefresh) {
            true -> remoteDataSource.fetchEventsByPage(username, pageIndex, perPage)
                    .flatMap { result ->
                        when (result is Result.Success) {
                            true -> localDataSource.clearLocalEventData()
                                    .andThen { localDataSource.insertNewEventData(result.data) }
                                    .andThen(Flowable.just(result))
                            else -> Flowable.just(result)
                        }
                    }
            false -> remoteDataSource.fetchEventsByPage(username, pageIndex, perPage)
                    .flatMap { result ->
                        when (result is Result.Success) {
                            true -> localDataSource.insertNewEventData(result.data)
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
                .compose(filterEvents())        // except the MemberEvent
    }

    private fun filterEvents(): FlowableTransformer<List<ReceivedEvent>, List<ReceivedEvent>> =
            FlowableTransformer { datas ->
                datas.flatMap { Flowable.fromIterable(it) }
                        .filter { DISPLAY_EVENT_TYPES.contains(it.type) }
                        .toList()
                        .toFlowable()
            }
}

class HomeLocalDataSource(private val db: UserDatabase) : ILocalDataSource {

    fun fetchPagedListFromDb(): Flowable<PagedList<ReceivedEvent>> {
        return db.userReceivedEventDao().queryEvents()
                .toRxPagedList()
    }

    fun clearLocalEventData(): Completable {
        return Completable
                .fromAction {
                    db.runInTransaction {
                        db.userReceivedEventDao().clearReceivedEvents()
                    }
                }
                .subscribeOn(RxSchedulers.database)
    }

    fun insertNewEventData(newPage: List<ReceivedEvent>): Completable {
        return Completable
                .fromAction { insertDataInternal(newPage) }
                .subscribeOn(RxSchedulers.database)
    }

    private fun insertDataInternal(newPage: List<ReceivedEvent>) {
        db.runInTransaction {
            val start = db.userReceivedEventDao().getNextIndexInReceivedEvents()
            val items = newPage.mapIndexed { index, child ->
                child.indexInResponse = start + index
                child
            }
            db.userReceivedEventDao().insert(items)
        }
    }
}