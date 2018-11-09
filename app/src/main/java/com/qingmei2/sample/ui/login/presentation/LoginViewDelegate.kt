package com.qingmei2.sample.ui.login.presentation

import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.base.viewdelegates.BaseViewDelegate

@SuppressWarnings("CheckResult")
class LoginViewDelegate(
        val viewModel: LoginViewModel,
        private val navigator: LoginNavigator
) : BaseViewDelegate() {

    init {
        viewModel.userInfo
                .toFlowable()
                .subscribe { _ ->
                    navigator.toMain()
                }
    }

    fun login() = viewModel.login()

}