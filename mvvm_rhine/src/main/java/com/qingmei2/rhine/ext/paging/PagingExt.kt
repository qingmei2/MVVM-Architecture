package com.qingmei2.rhine.ext.paging

import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

object Paging {

    fun <T> buildReactiveStream(
            intPageKeyedDataSource: IntPageKeyedDataSource<T>,
            enablePlaceholders: Boolean = false,
            pageSize: Int = PAGING_DEFAULT_PAGE_SIZE,
            initialLoadSizeHint: Int = PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT,
            prefetchDistance: Int = PAGING_DEFAULT_PREFETCH_DISTANCE
    ): Flowable<PagedList<T>> =
            buildRxJavaPagedList(
                    dataSourceFactory = intPageKeyedDataSource(dataSource = intPageKeyedDataSource),
                    enablePlaceholders = enablePlaceholders,
                    pageSize = pageSize,
                    initialLoadSizeHint = initialLoadSizeHint,
                    prefetchDistance = prefetchDistance
            )

    private fun <T> intPageKeyedDataSource(
            dataSource: IntPageKeyedDataSource<T>
    ): DataSource.Factory<Int, T> =
            object : DataSource.Factory<Int, T>() {

                override fun create(): DataSource<Int, T> = dataSource
            }

    private fun <T> buildRxJavaPagedList(
            dataSourceFactory: DataSource.Factory<Int, T>,
            enablePlaceholders: Boolean,
            pageSize: Int,
            initialLoadSizeHint: Int,
            prefetchDistance: Int
    ): Flowable<PagedList<T>> =
            RxPagedListBuilder<Int, T>(
                    dataSourceFactory,
                    PagedList.Config
                            .Builder()
                            .setInitialLoadSizeHint(initialLoadSizeHint)
                            .setPageSize(pageSize)
                            .setPrefetchDistance(prefetchDistance)
                            .setEnablePlaceholders(enablePlaceholders)
                            .build()
            ).buildFlowable(BackpressureStrategy.LATEST)
}

fun <T> DataSource.Factory<Int, T>.toRxPagedList(): Flowable<PagedList<T>> {
    return RxPagedListBuilder<Int, T>(
            this,
            PagedList.Config
                    .Builder()
                    .setInitialLoadSizeHint(PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT)
                    .setPageSize(PAGING_DEFAULT_PAGE_SIZE)
                    .setPrefetchDistance(PAGING_DEFAULT_PREFETCH_DISTANCE)
                    .setEnablePlaceholders(false)
                    .build()
    ).buildFlowable(BackpressureStrategy.LATEST)
}

const val PAGING_DEFAULT_PAGE_SIZE = 15
const val PAGING_DEFAULT_PREFETCH_DISTANCE = 15
const val PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT = 30