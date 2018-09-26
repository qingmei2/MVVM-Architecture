package com.qingmei2.sample.ui.main

import androidx.navigation.fragment.NavHostFragment
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.ui.main.home.presentation.HomeFragment
import com.qingmei2.sample.ui.main.profile.presentation.ProfileFragment
import com.qingmei2.sample.ui.main.task.TaskFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein.Module
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

val mainKodeinModule = Module(MAIN_MODULE_TAG) {

    bind<HomeFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        HomeFragment()
    }

    bind<TaskFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        TaskFragment()
    }

    bind<ProfileFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        ProfileFragment()
    }

    bind<NavHostFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        instance<MainActivity>().navHostFragment as NavHostFragment
    }

    bind<MainNavigator>() with scoped(AndroidComponentsWeakScope).singleton {
        MainNavigator(instance(), instance<MainActivity>().navigation)
    }

    bind<MainViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        MainViewModel().apply {
            addLifecycle(instance<MainActivity>())
        }
    }

    bind<MainViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        MainViewDelegate(instance(), instance())
    }
}