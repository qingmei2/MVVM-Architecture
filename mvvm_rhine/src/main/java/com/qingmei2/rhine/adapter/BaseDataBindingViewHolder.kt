package com.qingmei2.rhine.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseDataBindingViewHolder<T : Any, DB : ViewDataBinding>(
        view: View,
        bindBinding: (View) -> DB,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.ViewHolder(view) {

    private var binding: DB? = null

    init {
        binding = bindBinding(view)
    }

    fun bind(data: T, position: Int) {
        callback(data, binding!!, position)
    }
}
