package com.qingmei2.sample.base

import android.app.ProgressDialog
import android.databinding.ViewDataBinding
import com.qingmei2.rhine.BR
import com.qingmei2.rhine.base.fragment.RhineBaseFragment
import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel

abstract class BaseFragment<B : ViewDataBinding, VM : RhineBaseViewModel> : RhineBaseFragment<B, VM>() {

    protected var progressDialog: ProgressDialog? = null

//    protected fun onStateChanged(state: RhineBaseViewModel.State) {
//        when (state) {
//            RhineBaseViewModel.State.LOAD_ING -> showLoading()
//            RhineBaseViewModel.State.LOAD_WAIT,
//            RhineBaseViewModel.State.LOAD_SUCCESS,
//            RhineBaseViewModel.State.LOAD_FAILED -> hideLoading()
//        }
//    }

    override fun showLoading() {
        progressDialog = ProgressDialog(activity)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun variableId(): Int = BR.viewModel
}