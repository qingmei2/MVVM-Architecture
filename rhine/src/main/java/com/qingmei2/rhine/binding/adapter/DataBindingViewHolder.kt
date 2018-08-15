package com.qingmei2.rhine.binding.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class DataBindingViewHolder<T : ViewDataBinding>(val dataBinding: T) : RecyclerView.ViewHolder(dataBinding.root)
