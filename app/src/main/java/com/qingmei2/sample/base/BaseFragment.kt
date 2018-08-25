package com.qingmei2.sample.base

import android.app.ProgressDialog
import android.databinding.ViewDataBinding
import com.qingmei2.rhine.BR
import com.qingmei2.rhine.base.fragment.RhineBaseFragment
import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel

abstract class BaseFragment<B : ViewDataBinding, VM : RhineBaseViewModel> : RhineBaseFragment<B, VM>() {

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun variableId(): Int = BR.viewModel
}