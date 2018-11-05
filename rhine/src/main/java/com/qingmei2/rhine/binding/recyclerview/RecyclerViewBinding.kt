package com.qingmei2.rhine.binding.recyclerview

import android.annotation.SuppressLint
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import indi.yume.tools.dsladapter.RendererAdapter
import java.util.concurrent.TimeUnit

@BindingAdapter("bind_adapter")
fun bindAdapter(recyclerView: RecyclerView, adapter: RendererAdapter?) {
    if (adapter == null) return

    val old = recyclerView.adapter
    if (old is RendererAdapter)
        old.autoUpdateAdapter()
                .dispatchUpdatesTo(adapter)
    else
        recyclerView.adapter = adapter
}

@SuppressLint("CheckResult")
@BindingAdapter(
        "bind_scrollStateChanges",
        "bind_scrollStateChanges_debounce",
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