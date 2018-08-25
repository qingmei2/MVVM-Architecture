package com.qingmei2.rhine.base.acitivty

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.qingmei2.rhine.base.IView
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class RhineActivity<B : ViewDataBinding, D : IViewDelegate> : AppCompatActivity(),
        KodeinAware, IView {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

    protected lateinit var binding: B

    protected lateinit var screenDelegate: D

    abstract val layoutId: Int

    abstract val delegateId: Int

    abstract val instanceDelegate: () -> D

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    @CallSuper
    protected fun initBinding() {
        screenDelegate = instanceDelegate()

        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            setLifecycleOwner(this@RhineActivity)
            setVariable(delegateId, screenDelegate)
        }
    }
}
