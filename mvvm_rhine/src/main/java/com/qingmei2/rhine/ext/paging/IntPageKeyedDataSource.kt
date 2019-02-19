package com.qingmei2.rhine.ext.paging

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource

@SuppressLint("CheckResult")
class IntPageKeyedDataSource<T>(
        private val loadInitial: IntPageKeyedDataInitialProvider<T>,
        private val loadAfter: IntPageKeyedDataEachTimeProvider<T>,
        private val onErrorAction: OnDataSourceFetchError = { }
) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        loadInitial(params)
                .subscribe(
                        { data ->
                            val (list, pageIndex, hasAdjacentPageKey) = data
                            when (hasAdjacentPageKey) {
                                true -> {
                                    callback.onResult(list, pageIndex, pageIndex + 1)
                                }
                                false -> {
                                    callback.onResult(list, pageIndex, null)
                                }
                            }
                        },
                        { throwable ->
                            onErrorAction(throwable)
                        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        loadAfter(params)
                .subscribe(
                        { data ->
                            val (list, pageIndex, hasAdjacentPageKey) = data
                            when (hasAdjacentPageKey) {
                                true -> {
                                    callback.onResult(list, pageIndex + 1)
                                }
                                false -> {
                                    callback.onResult(list, null)
                                }
                            }
                        },
                        { throwable ->
                            onErrorAction(throwable)
                        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // do nothing
    }
}