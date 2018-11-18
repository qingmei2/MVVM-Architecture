package com.qingmei2.rhine.ext.paging

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.qingmei2.rhine.adapter.CommonLoadMoreDataSource
import com.qingmei2.rhine.ext.paging.Paging.DEFAULT_INITIAL_LOAD_SIZE_HINT
import com.qingmei2.rhine.ext.paging.Paging.DEFAULT_PAGE_SIZE
import com.qingmei2.rhine.ext.paging.Paging.DEFAULT_PREFETCH_DISTANCE
import io.reactivex.Flowable

object Paging {

    fun <T> dataSource(dataSourceProvider: (Int) -> Flowable<List<T>>): DataSource.Factory<Int, T> =
            object : DataSource.Factory<Int, T>() {

                override fun create(): DataSource<Int, T> =
                        CommonLoadMoreDataSource(dataSourceProvider)
            }

    const val DEFAULT_PAGE_SIZE = 15
    const val DEFAULT_PREFETCH_DISTANCE = DEFAULT_PAGE_SIZE
    const val DEFAULT_INITIAL_LOAD_SIZE_HINT = 30
}

fun <T> DataSource.Factory<Int, T>.toLiveData(
        enablePlaceholders: Boolean = false,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        initialLoadSizeHint: Int = DEFAULT_INITIAL_LOAD_SIZE_HINT,
        prefetchDistance: Int = DEFAULT_PREFETCH_DISTANCE
): LiveData<PagedList<T>> =
        LivePagedListBuilder<Int, T>(
                this,
                PagedList.Config
                        .Builder()
                        .setInitialLoadSizeHint(initialLoadSizeHint)
                        .setPageSize(pageSize)
                        .setPrefetchDistance(prefetchDistance)
                        .setEnablePlaceholders(enablePlaceholders)
                        .build()
        ).build()