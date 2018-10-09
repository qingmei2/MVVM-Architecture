package com.qingmei2.rhine.binding.support

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout

@BindingAdapter("refreshing")
fun isSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout,
                                   newValue: Boolean) {
    if (swipeRefreshLayout.isRefreshing != newValue)
        swipeRefreshLayout.isRefreshing = newValue
}

//@InverseBindingAdapter(attribute = "refreshing", event = "onRefreshAttrChanged")
//fun setSwipeRefreshLayoutRefreshing(swipeRefreshLayout: SwipeRefreshLayout): Boolean =
//        swipeRefreshLayout.isRefreshing

@BindingAdapter("onRefreshListener")
fun setOnRefreshListener(swipeRefreshLayout: SwipeRefreshLayout,
                         refreshListener: Runnable?) {
    swipeRefreshLayout.setOnRefreshListener {
        refreshListener?.run()
    }
}