package com.qingmei2.rhine.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

abstract class BaseFragment<B : ViewDataBinding> : InjectionFragment() {

    private lateinit var mRootView: View

    protected lateinit var binding: B

    abstract val layoutId: Int

    abstract val viewDelegate: IViewDelegate

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        lifecycle.addObserver(viewDelegate)
        initView()
    }

    abstract fun initView()

    private fun initBinding(rootView: View) {
        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setLifecycleOwner(this@BaseFragment)
        }
    }
}
