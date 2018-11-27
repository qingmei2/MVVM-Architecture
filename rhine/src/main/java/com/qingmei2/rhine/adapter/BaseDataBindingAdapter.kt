package com.qingmei2.rhine.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class BaseDataBindingAdapter<T : Any, DB : ViewDataBinding>(
        private val layoutId: Int,
        private val dataSource: () -> List<T>,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.Adapter<BaseDataBindingViewHolder<T, DB>>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseDataBindingViewHolder<T, DB> =
            BaseDataBindingViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context), layoutId, parent, false
                    ),
                    callback
            )

    override fun onBindViewHolder(holder: BaseDataBindingViewHolder<T, DB>, position: Int) =
            holder.bind(dataSource()[position], position)

    override fun getItemCount(): Int = dataSource().size

    fun forceUpdate() {
        notifyDataSetChanged()
    }
}