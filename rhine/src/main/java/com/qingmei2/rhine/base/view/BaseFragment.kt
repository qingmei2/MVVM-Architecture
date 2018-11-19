package com.qingmei2.rhine.base.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qingmei2.rhine.base.viewdelegate.IViewDelegate
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseFragment<B : ViewDataBinding, VD : IViewDelegate> : Fragment(),
        KodeinAware, IView {

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)

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
