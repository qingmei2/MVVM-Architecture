package com.qingmei2.rhine.binding.support

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("refreshing")
fun isSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout,
                                   newValue: Boolean) {
    if (swipeRefreshLayout.isRefreshing != newValue)
        swipeRefreshLayout.isRefreshing = newValue
}

@BindingAdapter("onRefreshListener")
fun setOnRefreshListener(swipeRefreshLayout: SwipeRefreshLayout,
                         refreshListener: Runnable?) {
    swipeRefreshLayout.setOnRefreshListener {
        refreshListener?.run()
    }
}