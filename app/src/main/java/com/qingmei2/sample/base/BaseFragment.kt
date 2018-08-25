package com.qingmei2.sample.base

import android.databinding.ViewDataBinding
import com.qingmei2.rhine.base.IScreenDelegate
import com.qingmei2.rhine.base.fragment.RhineBaseFragment

abstract class BaseFragment<B : ViewDataBinding, D : IScreenDelegate> : RhineBaseFragment<B, D>() {

    override fun showLoading() {
    }

    override fun hideLoading() {

    }
}