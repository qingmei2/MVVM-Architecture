package com.qingmei2.sample.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.qingmei2.rhine.base.view.RhineActivity
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

abstract class BaseActivity<B : ViewDataBinding, VD : IViewDelegate> : RhineActivity<B, VD>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    abstract fun initView()
}