package com.qingmei2.sample.ui.main

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.ui.main.home.presentation.HomeFragment
import com.qingmei2.sample.ui.main.profile.presentation.ProfileFragment
import com.qingmei2.sample.ui.main.repos.presentation.ReposFragment
import org.kodein.di.Kodein.Module
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

val mainKodeinModule = Module(MAIN_MODULE_TAG) {

    bind<HomeFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        HomeFragment()
    }

    bind<ReposFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        ReposFragment()
    }

    bind<ProfileFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        ProfileFragment()
    }

    bind<MainNavigator>() with scoped(AndroidComponentsWeakScope).singleton {
        MainNavigator()
    }

    bind<MainViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        MainViewModel().apply {
            addLifecycle(instance<MainFragment>())
        }
    }

    bind<MainViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        MainViewDelegate(instance(), instance())
    }
}