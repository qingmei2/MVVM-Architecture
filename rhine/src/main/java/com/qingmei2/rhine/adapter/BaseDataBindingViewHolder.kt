package com.qingmei2.rhine.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseDataBindingViewHolder<T : Any, DB : ViewDataBinding>(
        val binding: DB,
        val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: T, position: Int) {
        callback(data, binding, position)
    }
}
