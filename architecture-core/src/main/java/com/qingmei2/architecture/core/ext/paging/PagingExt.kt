package com.qingmei2.architecture.core.ext.paging

import androidx.annotation.AnyThread
import androidx.paging.PagingConfig

val globalPagingConfig: PagingConfig
    @AnyThread get() = PagingConfig(
            initialLoadSize = PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT,
            pageSize = PAGING_DEFAULT_PAGE_SIZE,
            prefetchDistance = PAGING_DEFAULT_PREFETCH_DISTANCE,
            enablePlaceholders = false
    )

private const val PAGING_DEFAULT_PAGE_SIZE = 15
private const val PAGING_DEFAULT_PREFETCH_DISTANCE = 15
private const val PAGING_DEFAULT_INITIAL_LOAD_SIZE_HINT = 30
