package com.qingmei2.sample.ui.main.home

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
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.http.globalHandleError
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import io.reactivex.Flowable

@SuppressLint("CheckResult")
class HomeRepository(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(remoteDataSource, localDataSource) {

    @MainThread
    fun fetchEventByPage(
            pageIndex: Int,
            remoteRequestPerPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Flowable<Result<List<ReceivedEvent>>> {
        val username: String = UserManager.INSTANCE.login
        return remoteDataSource.fetchEventsByPage(username, pageIndex, remoteRequestPerPage)
    }

    @WorkerThread
    fun clearAndInsertNewData(items: List<ReceivedEvent>) {
        localDataSource.clearAndInsertNewData(items)
    }

    @WorkerThread
    fun insertNewPageData(items: List<ReceivedEvent>) {
        localDataSource.insertNewPagedEventData(items)
    }

    @MainThread
    fun fetchEventDataSourceFactory(): DataSource.Factory<Int, ReceivedEvent> {
        return localDataSource.fetchPagedListFromLocal()
    }
}

class HomeRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {

    fun fetchEventsByPage(
            username: String,
            pageIndex: Int,
            perPage: Int
    ): Flowable<Result<List<ReceivedEvent>>> {
        return when (pageIndex) {
            1 ->
                fetchEventsByPageInternal(username, pageIndex, perPage)
                        .map { Result.success(it) }
                        .onErrorReturn { Result.failure(it) }
                        .startWith(Result.loading())   // step 2.show loading indicator
                        .startWith(Result.idle())      // step 1.reset loading indicator state
            else ->
                fetchEventsByPageInternal(username, pageIndex, perPage)
                        .map { Result.success(it) }
                        .onErrorReturn { Result.failure(it) }
        }
    }

    private fun fetchEventsByPageInternal(
            username: String,
            pageIndex: Int,
            perPage: Int
    ): Flowable<List<ReceivedEvent>> {
        return serviceManager.userService
                .queryReceivedEvents(username, pageIndex, perPage)
                .compose(globalHandleError())
                .subscribeOn(RxSchedulers.io)
    }
}

@SuppressLint("CheckResult")
class HomeLocalDataSource(private val db: UserDatabase) : ILocalDataSource {

    fun fetchPagedListFromLocal(): DataSource.Factory<Int, ReceivedEvent> {
        return db.userReceivedEventDao().queryEvents()
    }

    fun clearOldData() {
        db.runInTransaction {
            db.userReceivedEventDao().clearReceivedEvents()
        }
    }

    fun clearAndInsertNewData(data: List<ReceivedEvent>) {
        db.runInTransaction {
            db.userReceivedEventDao().clearReceivedEvents()
            insertDataInternal(data)
        }
    }

    fun insertNewPagedEventData(newPage: List<ReceivedEvent>) {
        db.runInTransaction { insertDataInternal(newPage) }
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