package com.qingmei2.rhine.adapter

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import com.qingmei2.rhine.ext.paging.OnDataSourceFetchError
import io.reactivex.Flowable

@SuppressLint("CheckResult")
class CommonLoadMoreDataSource<T>(
        private val dataSourceProvider: (Int) -> Flowable<List<T>>,
        private val onErrorAction: OnDataSourceFetchError
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
                }, { throwable ->
                    onErrorAction(throwable)
                })
    }
}