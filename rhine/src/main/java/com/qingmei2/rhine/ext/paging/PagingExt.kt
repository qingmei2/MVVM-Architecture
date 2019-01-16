package com.qingmei2.rhine.ext.paging

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.qingmei2.rhine.adapter.CommonLoadMoreDataSource
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

object Paging {

    fun <T> buildLiveData(
            dataSourceProvider: DataSourceProvider<T>,
            onErrorAction: OnDataSourceFetchError = { },
            enablePlaceholders: Boolean = false,
            pageSize: Int = DEFAULT_PAGE_SIZE,
            initialLoadSizeHint: Int = DEFAULT_INITIAL_LOAD_SIZE_HINT,
            prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE
    ): LiveData<PagedList<T>> =
            buildLiveDataPageList(
                    buildDataSource(
                            dataSourceProvider = dataSourceProvider,
                            onErrorAction = onErrorAction
                    ),
                    enablePlaceholders,
                    pageSize,
                    initialLoadSizeHint,
                    prefetchDistance
            )

    fun <T> buildReactiveStream(
            dataSourceProvider: DataSourceProvider<T>,
            onErrorAction: OnDataSourceFetchError = { },
            enablePlaceholders: Boolean = false,
            pageSize: Int = DEFAULT_PAGE_SIZE,
            initialLoadSizeHint: Int = DEFAULT_INITIAL_LOAD_SIZE_HINT,
            prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE
    ): Flowable<PagedList<T>> =
            buildRxJavaPagedList(
                    buildDataSource(
                            dataSourceProvider = dataSourceProvider,
                            onErrorAction = onErrorAction
                    ),
                    enablePlaceholders,
                    pageSize,
                    initialLoadSizeHint,
                    prefetchDistance
            )

    private fun <T> buildDataSource(
            dataSourceProvider: DataSourceProvider<T>,
            onErrorAction: OnDataSourceFetchError
    ): DataSource.Factory<Int, T> =
            object : DataSource.Factory<Int, T>() {

                override fun create(): DataSource<Int, T> =
                        CommonLoadMoreDataSource(
                                dataSourceProvider = dataSourceProvider,
                                onErrorAction = onErrorAction
                        )
            }

    private fun <T> buildRxJavaPagedList(
            dataSourceFactory: DataSource.Factory<Int, T>,
            enablePlaceholders: Boolean = false,
            pageSize: Int = DEFAULT_PAGE_SIZE,
            initialLoadSizeHint: Int = DEFAULT_INITIAL_LOAD_SIZE_HINT,
            prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE
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

    private fun <T> buildLiveDataPageList(
            dataSourceFactory: DataSource.Factory<Int, T>,
            enablePlaceholders: Boolean = false,
            pageSize: Int = DEFAULT_PAGE_SIZE,
            initialLoadSizeHint: Int = DEFAULT_INITIAL_LOAD_SIZE_HINT,
            prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE
    ): LiveData<PagedList<T>> =
            LivePagedListBuilder<Int, T>(
                    dataSourceFactory,
                    PagedList.Config
                            .Builder()
                            .setInitialLoadSizeHint(initialLoadSizeHint)
                            .setPageSize(pageSize)
                            .setPrefetchDistance(prefetchDistance)
                            .setEnablePlaceholders(enablePlaceholders)
                            .build()
            ).build()

    private const val DEFAULT_PAGE_SIZE = 15
    private const val DEFAULT_PREFETCH_DISTANCE = DEFAULT_PAGE_SIZE
    private const val DEFAULT_INITIAL_LOAD_SIZE_HINT = 30
}

typealias DataSourceProvider<T> = (Int) -> Flowable<Pair<List<T>, Boolean>>

typealias OnDataSourceFetchError = (Throwable) -> Unit