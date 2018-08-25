package com.qingmei2.rhine.base.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import com.qingmei2.rhine.base.IView

abstract class RhineFragment<B : ViewDataBinding, D : IViewDelegate> : Fragment(), IView {

    protected lateinit var mRootView: View

    protected lateinit var binding: B

    protected lateinit var screenDelegate: D

    abstract val layoutId: Int

    abstract val variableId: Int

    abstract val instanceDelegate: () -> D

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    @CallSuper
    protected fun initBinding(rootView: View) {
        screenDelegate = instanceDelegate()

        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setLifecycleOwner(this@RhineFragment)
            setVariable(variableId, screenDelegate)
        }
    }
}
