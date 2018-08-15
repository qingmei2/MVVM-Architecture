package com.qingmei2.rhine.binding.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * Created by QingMei on 2017/10/17.
 * desc:
 */

class DataBindingViewHolder<T : ViewDataBinding>(val dataBinding: T) : RecyclerView.ViewHolder(dataBinding.root)
