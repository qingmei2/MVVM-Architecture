package com.qingmei2.rhine.base.fragment

import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qingmei2.rhine.base.IView

import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel

abstract class RhineBaseFragment<B : ViewDataBinding, VM : RhineBaseViewModel> : Fragment(), IView {

    protected lateinit var binding: B

    protected lateinit var viewModel: VM

    protected lateinit var mRootView: View

    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initDatabinding(view)
        initView(view)
        initData()
    }

    @CallSuper
    protected fun initDatabinding(rootView: View) {
        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setLifecycleOwner(this@RhineBaseFragment)
            setVariable(variableId(), viewModel)
        }
    }

    protected fun initViewModel(): VM = ViewModelProviders.of(this)
            .get(viewModel::class.java)
            .apply {
                viewModel = this
                initLifecycleOwner(this@RhineBaseFragment)
            }

    protected abstract fun variableId(): Int

    protected abstract fun initView(view: View)

    protected abstract fun initData()
}
