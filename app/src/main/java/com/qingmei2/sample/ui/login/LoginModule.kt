package com.qingmei2.sample.ui.login

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

val loginKodeinModule = Kodein.Module(LOGIN_MODULE_TAG) {

    bind<LoginNavigator>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginNavigator(instance<LoginFragment>().activity!!)
    }

    bind<LoginViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginViewModel(instance()).apply {
            addLifecycle(instance<LoginFragment>())
        }
    }

    bind<LoginViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        LoginViewDelegate(
                viewModel = instance(),
                navigator = instance(),
                loadingView = instance<LoginFragment>().loginForm,
                lifecycleOwner = instance<LoginFragment>()
        )
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