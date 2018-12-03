package com.qingmei2.sample.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.support.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

val loginKodeinModule = Kodein.Module(LOGIN_MODULE_TAG) {

    bind<LoginNavigator>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        LoginNavigator(context.activity!!)
    }

    bind<LoginViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ViewModelProviders
                .of(context.activity!!, LoginViewModelFactory(instance()))
                .get(LoginViewModel::class.java).apply {
                    addLifecycle(context)
                }
    }

    bind<LoginViewDelegate>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        LoginViewDelegate(
                viewModel = instance(),
                navigator = instance(),
                loadingViewModel = CommonLoadingViewModel.instance(context)
        )
    }

    bind<LoginRemoteDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        LoginRemoteDataSource(instance())
    }

    bind<LoginLocalDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        LoginLocalDataSource(instance(), instance())
    }

    bind<LoginDataSourceRepository>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        LoginDataSourceRepository(instance(), instance())
    }
}