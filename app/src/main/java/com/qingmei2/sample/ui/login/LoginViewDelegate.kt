package com.qingmei2.sample.ui.login

import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.base.viewdelegates.BaseLoadingViewDelegate
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel

@SuppressWarnings("CheckResult")
class LoginViewDelegate(
        val viewModel: LoginViewModel,
        private val navigator: LoginNavigator,
        loadingViewModel: CommonLoadingViewModel
) : BaseLoadingViewDelegate(loadingViewModel) {

    init {
        viewModel.userInfo
                .toFlowable()
                .doOnNext { navigator.toMain() }
                .bindLifecycle(viewModel)
                .subscribe()

        viewModel.loadingLayout
                .toFlowable()
                .doOnNext { applyState(it) }
                .bindLifecycle(loadingViewModel)
                .subscribe()
    }

    fun login() = viewModel.login()
}