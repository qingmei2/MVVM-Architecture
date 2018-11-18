package com.qingmei2.rhine.adapter

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class BasePagingAdapter<T : Any, DB : ViewDataBinding>(
        private val layoutId: Int,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> },
        diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {

            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
                    oldItem == newItem
        }

) : PagedListAdapter<T, BaseViewHolder<T, DB>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseViewHolder<T, DB> =
            BaseViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context), layoutId, parent, false
                    ), callback
            )

    override fun onBindViewHolder(holder: BaseViewHolder<T, DB>, position: Int) =
            holder.bind(getItem(position) as T, position)
}

class BaseViewHolder<T : Any, DB : ViewDataBinding>(
        val binding: DB,
        val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: T, position: Int) {
        callback(data, binding, position)
    }
}

