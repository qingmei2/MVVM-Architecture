package com.qingmei2.rhine.base.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

abstract class BaseActivity<B : ViewDataBinding> : BaseInjectActivity() {

    protected lateinit var binding: B

    abstract val layoutId: Int

    abstract val viewDelegate: IViewDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        lifecycle.addObserver(viewDelegate)
        initView()
    }

    abstract fun initView()

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            setLifecycleOwner(this@BaseActivity)
        }
    }
}