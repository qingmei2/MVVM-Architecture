package com.qingmei2.sample.login

import com.qingmei2.rhine.base.viewdelegate.IViewDelegate

class LoginViewDelegate(
        val viewModel: LoginViewModel,
        val navigator: LoginNavigator
) : IViewDelegate {
}