package com.qingmei2.sample.ui.main.home

import android.annotation.SuppressLint
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.paging.DataSource
import androidx.room.withTransaction
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.base.Results
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.utils.processApiResponse
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeRepository @Inject constructor(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(remoteDataSource, localDataSource) {

    @MainThread
    suspend fun fetchEventByPage(
            pageIndex: Int,
            remoteRequestPerPage: Int = PAGING_REMOTE_PAGE_SIZE
    ): Results<List<ReceivedEvent>> {
        val username: String = UserManager.INSTANCE.login
        return remoteDataSource.fetchEventsByPage(username, pageIndex, remoteRequestPerPage)
    }

    @AnyThread
    suspend fun clearAndInsertNewData(items: List<ReceivedEvent>) {
        localDataSource.clearAndInsertNewData(items)
    }

    @AnyThread
    suspend fun insertNewPageData(items: List<ReceivedEvent>) {
        localDataSource.insertNewPagedEventData(items)
    }

    @MainThread
    fun fetchEventDataSourceFactory(): DataSource.Factory<Int, ReceivedEvent> {
        return localDataSource.fetchPagedListFromLocal()
    }
}

class HomeRemoteDataSource @Inject constructor(private val serviceManager: ServiceManager) : IRemoteDataSource {

    suspend fun fetchEventsByPage(
            username: String,
            pageIndex: Int,
            perPage: Int
    ): Results<List<ReceivedEvent>> {
        return fetchEventsByPageInternal(username, pageIndex, perPage)
    }

    private suspend fun fetchEventsByPageInternal(
            username: String,
            pageIndex: Int,
            perPage: Int
    ): Results<List<ReceivedEvent>> {
        return processApiResponse {
            serviceManager.userService.queryReceivedEvents(username, pageIndex, perPage)
        }
    }
}

@SuppressLint("CheckResult")
class HomeLocalDataSource @Inject constructor(private val db: UserDatabase) : ILocalDataSource {

    fun fetchPagedListFromLocal(): DataSource.Factory<Int, ReceivedEvent> {
        return db.userReceivedEventDao().queryEvents()
    }

    suspend fun clearOldData() {
        db.withTransaction {
            db.userReceivedEventDao().clearReceivedEvents()
        }
    }

    suspend fun clearAndInsertNewData(data: List<ReceivedEvent>) {
        db.withTransaction {
            db.userReceivedEventDao().clearReceivedEvents()
            insertDataInternal(data)
        }
    }

    suspend fun insertNewPagedEventData(newPage: List<ReceivedEvent>) {
        db.withTransaction { insertDataInternal(newPage) }
    }

    private suspend fun insertDataInternal(newPage: List<ReceivedEvent>) {
        val start = db.userReceivedEventDao().getNextIndexInReceivedEvents() ?: 0
        val items = newPage.mapIndexed { index, child ->
            child.indexInResponse = start + index
            child
        }
        db.userReceivedEventDao().insert(items)
    }
}
