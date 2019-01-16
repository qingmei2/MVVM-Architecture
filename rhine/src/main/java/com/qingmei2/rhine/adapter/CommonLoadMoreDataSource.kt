package com.qingmei2.rhine.adapter

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.qingmei2.rhine.ext.paging.OnDataSourceFetchError
import io.reactivex.Flowable

@SuppressLint("CheckResult")
class CommonLoadMoreDataSource<T>(
        private val dataSourceProvider: (Int) -> Flowable<Pair<List<T>, Boolean>>,
        private val onErrorAction: OnDataSourceFetchError
) : PageKeyedDataSource<Int, T>() {

    private var pageIndex = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        loadDataSourceInternal { pair ->
            val (list, hasAdjacentPageKey) = pair
            when (hasAdjacentPageKey) {
                true -> {
                    callback.onResult(list, pageIndex, ++pageIndex)
                }
                false -> {
                    callback.onResult(list, pageIndex, null)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        loadDataSourceInternal { pair ->
            val (list, hasAdjacentPageKey) = pair
            when (hasAdjacentPageKey) {
                true -> {
                    callback.onResult(list, ++pageIndex)
                }
                false -> {
                    callback.onResult(list, null)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // do nothing
    }

    private fun loadDataSourceInternal(successCallback: (Pair<List<T>, Boolean>) -> Unit) {
        dataSourceProvider(pageIndex)
                .subscribe({ pair ->
                    successCallback(pair)
                }, { throwable ->
                    onErrorAction(throwable)
                })
    }
}