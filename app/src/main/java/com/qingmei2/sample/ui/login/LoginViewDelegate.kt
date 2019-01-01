package com.qingmei2.sample.ui.login

import com.qingmei2.rhine.ext.livedata.toReactiveX
import com.qingmei2.sample.base.viewdelegates.BaseLoadingViewDelegate
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import com.uber.autodispose.autoDisposable

@SuppressWarnings("CheckResult")
class LoginViewDelegate(
        val viewModel: LoginViewModel,
        private val navigator: LoginNavigator,
        loadingViewModel: CommonLoadingViewModel
) : BaseLoadingViewDelegate(loadingViewModel) {

    init {
        viewModel.userInfo
                .toReactiveX()
                .doOnNext { navigator.toMain() }
                .autoDisposable(viewModel)
                .subscribe()

        viewModel.loadingLayout
                .toReactiveX()
                .doOnNext { applyState(it) }
                .autoDisposable(loadingViewModel)
                .subscribe()
    }

    fun login() = viewModel.login()
}