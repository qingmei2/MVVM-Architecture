package com.qingmei2.sample.login

import com.qingmei2.rhine.ext.viewmodel.viewModel
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
        instance<LoginActivity>().viewModel(LoginViewModel::class.java)
    }

    bind<LoginViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginViewDelegate(instance(), instance())
    }
}