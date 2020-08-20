package com.qingmei2.architecture.core.ext.paging

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagingConfig

fun <Key : Any, Value : Any> DataSource.Factory<Key, Value>.toLiveDataPagedList(
        config: PagedList.Config? = null,
        boundaryCallback: PagedList.BoundaryCallback<Value>? = null
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(
            this, config ?: PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT)
            .setPageSize(PAGING_DEFAULT_PAGE_SIZE)
            .setPrefetchDistance(PAGING_DEFAULT_PREFETCH_DISTANCE)
            .setEnablePlaceholders(false)
            .build()
    )
            .setBoundaryCallback(boundaryCallback)
            .build()
}

fun getPagingConfig(): PagingConfig {
    return PagingConfig(
            initialLoadSize = PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT,
            pageSize = PAGING_DEFAULT_PAGE_SIZE,
            prefetchDistance = PAGING_DEFAULT_PREFETCH_DISTANCE,
            enablePlaceholders = false
    )
}

private const val PAGING_DEFAULT_PAGE_SIZE = 15
private const val PAGING_DEFAULT_PREFETCH_DISTANCE = 15
private const val PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT = 30