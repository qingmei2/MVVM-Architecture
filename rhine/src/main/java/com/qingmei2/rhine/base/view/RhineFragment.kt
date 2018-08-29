package com.qingmei2.rhine.base.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein

abstract class RhineFragment<B : ViewDataBinding, D : IViewDelegate> : Fragment(),
        KodeinAware, IView {

    override val kodein by closestKodein()

    protected lateinit var mRootView: View

    protected lateinit var binding: B

    protected lateinit var viewDelegate: D

    abstract val layoutId: Int

    abstract val delegateSupplier: () -> D

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
    }

    @CallSuper
    protected fun initBinding(rootView: View) {
        binding = DataBindingUtil.bind(rootView)!!
        viewDelegate = delegateSupplier()
        with(binding) {
            setLifecycleOwner(this@RhineFragment)
        }
    }
}
