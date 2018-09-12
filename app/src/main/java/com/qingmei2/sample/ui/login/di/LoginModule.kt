package com.qingmei2.sample.ui.login.di

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.ui.login.data.LoginDataSourceRepository
import com.qingmei2.sample.ui.login.data.LoginLocalDataSource
import com.qingmei2.sample.ui.login.data.LoginRemoteDataSource
import com.qingmei2.sample.ui.login.presentation.LoginActivity
import com.qingmei2.sample.ui.login.presentation.LoginNavigator
import com.qingmei2.sample.ui.login.presentation.LoginViewDelegate
import com.qingmei2.sample.ui.login.presentation.LoginViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

val loginKodeinModule = Kodein.Module(LOGIN_MODULE_TAG) {

    bind<LoginNavigator>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginNavigator(instance())
    }

    bind<LoginViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginViewModel(instance()).apply {
            addLifecycle(instance<LoginActivity>())
        }
    }

    bind<LoginViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginViewDelegate(instance(), instance())
    }

    bind<LoginRemoteDataSource>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginRemoteDataSource(instance())
    }

    bind<LoginLocalDataSource>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginLocalDataSource(instance(), instance())
    }

    bind<LoginDataSourceRepository>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginDataSourceRepository(instance(), instance())
    }
}