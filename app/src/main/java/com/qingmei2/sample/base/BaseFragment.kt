package com.qingmei2.sample.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.rhine.base.view.RhineFragment

abstract class BaseFragment<B : ViewDataBinding, VD : IViewDelegate> : RhineFragment<B, VD>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    abstract fun initView()
}