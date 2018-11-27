package com.qingmei2.rhine.adapter

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup

class BasePagingDataBindingAdapter<T : Any, DB : ViewDataBinding>(
        private val layoutId: Int,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> },
        diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {

            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem
        }

) : PagedListAdapter<T, BaseDataBindingViewHolder<T, DB>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseDataBindingViewHolder<T, DB> =
            BaseDataBindingViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context), layoutId, parent, false
                    ), callback
            )

    override fun onBindViewHolder(holder: BaseDataBindingViewHolder<T, DB>, position: Int) =
            holder.bind(getItem(position) as T, position)
}