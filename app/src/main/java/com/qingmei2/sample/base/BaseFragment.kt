package com.qingmei2.sample.base

import android.databinding.ViewDataBinding
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.rhine.base.fragment.RhineFragment

abstract class BaseFragment<B : ViewDataBinding, D : IViewDelegate> : RhineFragment<B, D>() {

    override fun showLoading() {
    }

    override fun hideLoading() {

    }
}