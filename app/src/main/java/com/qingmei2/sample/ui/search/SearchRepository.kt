package com.qingmei2.sample.ui.search

import androidx.paging.Pager
import androidx.paging.PagingSource
import com.qingmei2.architecture.core.base.repository.BaseRepositoryRemote
import com.qingmei2.architecture.core.base.repository.IRemoteDataSource
import com.qingmei2.architecture.core.ext.paging.globalPagingConfig
import com.qingmei2.sample.PAGING_REMOTE_PAGE_SIZE
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.http.service.UserService
import javax.inject.Inject

class SearchRepository @Inject constructor(
        remoteDataSource: SearchRemoteDataSource
) : BaseRepositoryRemote<SearchRemoteDataSource>(remoteDataSource) {

    fun fetchPager(keyWord: String): Pager<Int, Repo> {
        return remoteDataSource.getPager(keyWord)
    }
}

class SearchRemoteDataSource @Inject constructor(
        private val userService: UserService
) : IRemoteDataSource {

    fun getPager(keyWord: String): Pager<Int, Repo> {
        return Pager(
                config = globalPagingConfig,
                pagingSourceFactory = { SearchPagingSource(userService, keyWord) }
        )
    }
}

class SearchPagingSource(
        private val userService: UserService,
        private val keyWord: String
) : PagingSource<Int, Repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        if (params is LoadParams.Prepend) {
            return LoadResult.Page(
                    data = listOf(),
                    prevKey = null,
                    nextKey = null
            )
        }
        return try {
            val key = if (params.key == null) 1 else params.key as Int
            val searchResult = userService.search(keyWord, key, PAGING_REMOTE_PAGE_SIZE)
            LoadResult.Page(
                    data = searchResult.items,
                    prevKey = key - 1,
                    nextKey = key + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}
