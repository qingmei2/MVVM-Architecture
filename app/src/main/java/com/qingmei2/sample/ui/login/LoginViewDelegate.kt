package com.qingmei2.sample.ui.login

import android.arch.lifecycle.LifecycleOwner
import android.view.View
import com.qingmei2.rhine.ext.lifecycle.bindLifecycle
import com.qingmei2.rhine.ext.livedata.toFlowable
import com.qingmei2.sample.base.viewdelegates.BaseLoadingViewDelegate

@SuppressWarnings("CheckResult")
class LoginViewDelegate(
        val viewModel: LoginViewModel,
        private val navigator: LoginNavigator,
        loadingView: View,
        lifecycleOwner: LifecycleOwner
) : BaseLoadingViewDelegate(lifecycleOwner, loadingView) {

    init {
        viewModel.userInfo
                .toFlowable()
                .doOnNext { navigator.toMain() }
                .bindLifecycle(lifecycleOwner)
                .subscribe()

        viewModel.loadingLayout
                .toFlowable()
                .doOnNext { applyState(it) }
                .bindLifecycle(lifecycleOwner)
                .subscribe()

        loadingViewModel.loadingState
                .toFlowable()
                .bindLifecycle(lifecycleOwner)
                .subscribe()
    }

    fun login() = viewModel.login()
}