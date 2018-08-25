package com.qingmei2.sample.base

import android.databinding.ViewDataBinding
import com.qingmei2.rhine.base.IScreenDelegate
import com.qingmei2.rhine.base.acitivty.RhineBaseActivity

abstract class BaseActivity<B : ViewDataBinding, D : IScreenDelegate> : RhineBaseActivity<B, D>() {

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
}