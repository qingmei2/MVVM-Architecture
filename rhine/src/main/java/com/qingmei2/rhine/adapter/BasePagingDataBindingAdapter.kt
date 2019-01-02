package com.qingmei2.rhine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

class BasePagingDataBindingAdapter<T : Any, DB : ViewDataBinding>(
        private val layoutId: Int,
        private val bindBinding: (View) -> DB,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> },
        diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {

            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem
        }

) : PagedListAdapter<T, BaseDataBindingViewHolder<T, DB>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseDataBindingViewHolder<T, DB> {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BaseDataBindingViewHolder(view, bindBinding, callback)
    }

    override fun onBindViewHolder(holder: BaseDataBindingViewHolder<T, DB>, position: Int) =
            holder.bind(getItem(position) as T, position)
}