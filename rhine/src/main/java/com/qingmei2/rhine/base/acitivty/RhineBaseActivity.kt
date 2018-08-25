package com.qingmei2.rhine.base.acitivty

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.qingmei2.rhine.base.IView
import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class RhineBaseActivity<B : ViewDataBinding, VM : RhineBaseViewModel> : AppCompatActivity(),
        KodeinAware, IView {

    private val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein, copy = Copy.All)
    }

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initDatabinding()
        initData()
        initView()
    }

    @CallSuper
    protected fun initDatabinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            setLifecycleOwner(this@RhineBaseActivity)
            setVariable(variableId(), viewModel)
        }
    }

    protected fun initViewModel(): VM = ViewModelProviders.of(this)
            .get(viewModel::class.java)
            .apply {
                viewModel = this
                initLifecycleOwner(this@RhineBaseActivity)
            }

    protected abstract fun variableId(): Int

    protected abstract fun initData()

    protected abstract fun initView()
}
