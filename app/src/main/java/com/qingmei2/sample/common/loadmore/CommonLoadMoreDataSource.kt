package com.qingmei2.sample.common.loadmore

import android.annotation.SuppressLint
import android.arch.paging.PageKeyedDataSource
import io.reactivex.Flowable

@SuppressLint("CheckResult")
class CommonLoadMoreDataSource<T>(
        private val dataSourceProvider: (Int) -> Flowable<List<T>>
) : PageKeyedDataSource<Int, T>() {

    private var pageIndex = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        loadDataSourceInternal {
            callback.onResult(it, pageIndex, pageIndex + 1)
            pageIndex++
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        loadDataSourceInternal {
            callback.onResult(it, pageIndex + 1)
            pageIndex++
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

    }

    private fun loadDataSourceInternal(successCallback: (List<T>) -> Unit) {
        dataSourceProvider(pageIndex)
                .subscribe({ list ->
                    successCallback(list)
                }, {
                    // do nothing
                })
    }
}