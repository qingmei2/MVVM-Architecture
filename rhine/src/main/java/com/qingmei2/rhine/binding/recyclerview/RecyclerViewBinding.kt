package com.qingmei2.rhine.binding.recyclerview

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import java.util.concurrent.TimeUnit

@BindingAdapter("bind_recyclerView_adapter")
fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?) {
    adapter?.apply {
        recyclerView.adapter = adapter
    }
}

@SuppressLint("CheckResult")
@BindingAdapter(
        "bind_recyclerView_scrollStateChanges",
        "bind_recyclerView_scrollStateChanges_debounce",
        requireAll = false
)
fun setScrollStateChanges(recyclerView: RecyclerView,
                          listener: ScrollStateChangesListener,
                          debounce: Long = 500) {
    RxRecyclerView.scrollStateChanges(recyclerView)
            .debounce(debounce, TimeUnit.MILLISECONDS)
            .subscribe { state ->
                listener(state)
            }
}

typealias ScrollStateChangesListener = (Int) -> Unit