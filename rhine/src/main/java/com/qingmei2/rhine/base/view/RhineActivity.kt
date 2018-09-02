package com.qingmei2.rhine.base.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class RhineActivity<B : ViewDataBinding> : AppCompatActivity(),
        KodeinAware, IView {

    protected val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

    override val kodeinTrigger = KodeinTrigger()

    protected lateinit var binding: B

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        kodeinTrigger.trigger()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            setLifecycleOwner(this@RhineActivity)
        }
    }
}