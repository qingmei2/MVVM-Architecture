package com.qingmei2.rhine.base.fragment

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.qingmei2.rhine.base.viewmodel.BaseRhineViewModel

abstract class BaseFragment<B : ViewDataBinding, V : BaseRhineViewModel> : Fragment() {

    protected var binding: B? = null

    var viewModel: V? = null

    protected lateinit var mRootView: View

    protected var progressDialog: ProgressDialog? = null

    protected abstract val layoutRes: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = LayoutInflater.from(context).inflate(layoutRes, container, false)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        initView(view)
        initData()
    }

    protected fun onStateChanged(state: BaseRhineViewModel.State) {
        when (state) {
            BaseRhineViewModel.State.LOAD_WAIT -> {
            }
            BaseRhineViewModel.State.LOAD_ING -> loading(true)
            BaseRhineViewModel.State.LOAD_SUCCESS -> loading(false)
            BaseRhineViewModel.State.LOAD_FAILED -> loading(false)
            else -> {
            }
        }
    }

    protected fun loading(showing: Boolean) {
        if (context != null) {
            if (showing) {
                progressDialog = ProgressDialog(context)
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            } else if (progressDialog != null) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        }
    }

    protected abstract fun initView(view: View)

    protected abstract fun initData()

}
