package com.qingmei2.sample.ui.login

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

val loginKodeinModule = Kodein.Module(LOGIN_MODULE_TAG) {

    bind<LoginViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProvider(this.context, LoginViewModelFactory(instance())).get(LoginViewModel::class.java)
    }

    bind<LoginRemoteDataSource>() with singleton {
        LoginRemoteDataSource(instance())
    }

    bind<LoginLocalDataSource>() with singleton {
        LoginLocalDataSource(instance(), instance())
    }

    bind<LoginRepository>() with singleton {
        LoginRepository(instance(), instance())
    }
}