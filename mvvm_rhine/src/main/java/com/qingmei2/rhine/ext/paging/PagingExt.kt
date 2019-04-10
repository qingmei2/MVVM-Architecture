package com.qingmei2.rhine.ext.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.qingmei2.rhine.util.RxSchedulers
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Scheduler

fun <Key, Value> DataSource.Factory<Key, Value>.toRxPagedList(
        config: PagedList.Config? = null,
        boundaryCallback: PagedList.BoundaryCallback<Value>? = null,
        fetchSchedulers: Scheduler = RxSchedulers.io
): Flowable<PagedList<Value>> {
    return RxPagedListBuilder<Key, Value>(
            this, config ?: PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT)
            .setPageSize(PAGING_DEFAULT_PAGE_SIZE)
            .setPrefetchDistance(PAGING_DEFAULT_PREFETCH_DISTANCE)
            .setEnablePlaceholders(false)
            .build()
    )
            .setBoundaryCallback(boundaryCallback)
            .setFetchScheduler(fetchSchedulers)
            .buildFlowable(BackpressureStrategy.LATEST)
}

private const val PAGING_DEFAULT_PAGE_SIZE = 15
private const val PAGING_DEFAULT_PREFETCH_DISTANCE = 15
private const val PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT = 30