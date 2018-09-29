package com.qingmei2.rhine.binding.recyclerview

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import indi.yume.tools.dsladapter.RendererAdapter

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