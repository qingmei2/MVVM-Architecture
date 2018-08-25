package com.qingmei2.sample.base

import android.databinding.ViewDataBinding
import com.qingmei2.rhine.BR
import com.qingmei2.rhine.base.acitivty.RhineBaseActivity
import com.qingmei2.rhine.base.viewmodel.RhineBaseViewModel

abstract class BaseActivity<B : ViewDataBinding, VM : RhineBaseViewModel> : RhineBaseActivity<B, VM>() {

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun variableId(): Int = BR.viewModel
}