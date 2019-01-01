package com.qingmei2.rhine.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.qingmei2.rhine.BR

abstract class BaseFragment<B : ViewDataBinding> : InjectionFragment() {

    private lateinit var mRootView: View

    protected lateinit var binding: B

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        initView()
    }

    open fun initView() {

    }

    private fun initBinding(rootView: View) {
        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setVariable(BR.fragment, this@BaseFragment)
            setLifecycleOwner(this@BaseFragment)
        }
    }
}
