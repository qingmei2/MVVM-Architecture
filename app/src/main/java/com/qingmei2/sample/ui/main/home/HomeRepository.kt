package com.qingmei2.sample.ui.main.home

import android.annotation.SuppressLint
import androidx.paging.*
import androidx.room.withTransaction
import com.qingmei2.architecture.core.base.repository.BaseRepositoryBoth
import com.qingmei2.architecture.core.base.repository.ILocalDataSource
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.architecture.core.ext.paging.getPagingConfig
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.db.UserDatabase
import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.http.service.ServiceManager
import com.qingmei2.sample.http.service.UserService
import com.qingmei2.sample.manager.UserManager
import com.qingmei2.sample.utils.toast
import javax.inject.Inject

@SuppressLint("CheckResult")
class HomeRepository @Inject constructor(
        remoteDataSource: HomeRemoteDataSource,
        localDataSource: HomeLocalDataSource
) : BaseRepositoryBoth<HomeRemoteDataSource, HomeLocalDataSource>(remoteDataSource, localDataSource) {

    fun fetchPager(): Pager<Int, ReceivedEvent> {
        val username: String = UserManager.INSTANCE.login
        return Pager(
                config = getPagingConfig(),
                remoteMediator = remoteDataSource.fetchRemoteMediator(username, localDataSource)
        ) {
            localDataSource.fetchPagedListFromLocal()
        }
    }

}

class HomeRemoteDataSource @Inject constructor(private val serviceManager: ServiceManager) : IRemoteDataSource {

    fun fetchRemoteMediator(username: String, localDataSource: HomeLocalDataSource): HomeRemoteMediator {
        return HomeRemoteMediator(serviceManager.userService, username, localDataSource)
    }

}

@SuppressLint("CheckResult")
class HomeLocalDataSource @Inject constructor(private val db: UserDatabase) : ILocalDataSource {

    fun fetchPagedListFromLocal(): PagingSource<Int, ReceivedEvent> {
        return db.userReceivedEventDao().queryEvents()
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

    suspend fun fetchNextIndex(): Int {
        return db.withTransaction {
            db.userReceivedEventDao().getNextIndexInReceivedEvents() ?: 0
        }
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

@OptIn(ExperimentalPagingApi::class)
class HomeRemoteMediator(
        private val userService: UserService,
        private val username: String,
        private val localDataSource: HomeLocalDataSource
) : RemoteMediator<Int, ReceivedEvent>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ReceivedEvent>): MediatorResult {
        return try {
            val pageIndex = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val nextIndex = localDataSource.fetchNextIndex()
                    if (nextIndex % PAGING_REMOTE_PAGE_SIZE != 0) {
                        return MediatorResult.Success(true)
                    }
                    nextIndex / PAGING_REMOTE_PAGE_SIZE + 1
                }
            }
            val data = userService.queryReceivedEvents(username, pageIndex, PAGING_REMOTE_PAGE_SIZE)
            if (loadType == LoadType.REFRESH) {
                localDataSource.clearAndInsertNewData(data)
            } else {
                localDataSource.insertNewPagedEventData(data)
            }
            MediatorResult.Success(data.isEmpty())
        } catch (exception: Exception) {
            toast { exception.toString() }
            MediatorResult.Error(exception)
        }
    }

}
