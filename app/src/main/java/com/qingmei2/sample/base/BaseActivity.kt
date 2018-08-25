package com.qingmei2.sample.base

import android.app.ProgressDialog
import android.databinding.ViewDataBinding
import com.qingmei2.rhine.BR
import com.qingmei2.rhine.base.acitivty.RhineBaseActivity
import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel

abstract class BaseActivity<B : ViewDataBinding, VM : RhineBaseViewModel> : RhineBaseActivity<B, VM>() {

    protected var progressDialog: ProgressDialog? = null

    override fun showLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun variableId(): Int = BR.viewModel

}