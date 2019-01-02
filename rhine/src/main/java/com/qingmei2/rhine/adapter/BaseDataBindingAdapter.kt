package com.qingmei2.rhine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseDataBindingAdapter<T : Any, DB : ViewDataBinding>(
        private val layoutId: Int,
        private val dataSource: () -> List<T>,
        private val bindBinding: (View) -> DB,
        private val callback: (T, DB, Int) -> Unit = { _, _, _ -> }
) : RecyclerView.Adapter<BaseDataBindingViewHolder<T, DB>>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): BaseDataBindingViewHolder<T, DB> =
            BaseDataBindingViewHolder(
                    LayoutInflater.from(parent.context).inflate(layoutId, parent, false),
                    bindBinding,
                    callback
            )

    override fun onBindViewHolder(holder: BaseDataBindingViewHolder<T, DB>, position: Int) =
            holder.bind(dataSource()[position], position)

    override fun getItemCount(): Int = dataSource().size

    fun forceUpdate() {
        notifyDataSetChanged()
    }
}