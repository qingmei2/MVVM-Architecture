package com.qingmei2.rhine.base.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.kcontext

abstract class BaseActivity<B : ViewDataBinding, VD : IViewDelegate> : BaseInjectActivity() {

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