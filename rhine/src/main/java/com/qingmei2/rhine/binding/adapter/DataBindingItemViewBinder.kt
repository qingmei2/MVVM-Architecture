package com.qingmei2.rhine.binding.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

import com.annimon.stream.function.BiFunction
import com.qingmei2.rhine.BR

import me.drakeet.multitype.ItemViewBinder

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

class DataBindingItemViewBinder<T, DB : ViewDataBinding> : ItemViewBinder<T, DataBindingViewHolder<DB>> {

    private val delegate: Delegate<T, DB>

    constructor(delegate: Delegate<T, DB>) {
        this.delegate = delegate
    }

    constructor(factory: BiFunction<LayoutInflater, ViewGroup, DB>,
                binder: OnBindItem<T, DB>) : this(SimpleDelegate<T, DB>(factory, binder)) {
    }

    constructor(@LayoutRes resId: Int, binder: OnBindItem<T, DB>) : this({ inflater, parent -> DataBindingUtil.inflate(inflater, resId, parent, false) }, binder) {}

    override fun onCreateViewHolder(inflater: LayoutInflater,
                                    parent: ViewGroup): DataBindingViewHolder<DB> {
        return DataBindingViewHolder(delegate.onCreateDataBinding(inflater, parent))
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<DB>, item: T) {
        val binding = holder.dataBinding
        binding.setVariable(BR.data, item)
        delegate.onBind(binding, item, holder.adapterPosition)
        binding.executePendingBindings()
    }

    interface Delegate<T, DB : ViewDataBinding> {
        fun onCreateDataBinding(inflater: LayoutInflater, parent: ViewGroup): DB

        fun onBind(dataBinding: DB, item: T, position: Int)
    }

    interface OnBindItem<T, DB : ViewDataBinding> {
        fun bind(dataBinding: DB, data: T, position: Int)
    }

    private class SimpleDelegate<T, DB : ViewDataBinding> internal constructor(private val factory: BiFunction<LayoutInflater, ViewGroup, DB>, private val binder: OnBindItem<T, DB>) : Delegate<T, DB> {

        override fun onCreateDataBinding(inflater: LayoutInflater, parent: ViewGroup): DB {
            return factory.apply(inflater, parent)
        }

        override fun onBind(dataBinding: DB, item: T, position: Int) {
            binder.bind(dataBinding, item, position)
        }
    }
}
