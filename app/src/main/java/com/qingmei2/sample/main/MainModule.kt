package com.qingmei2.sample.main

import androidx.navigation.fragment.NavHostFragment
import com.qingmei2.rhine.ext.viewmodel.viewModel
import com.qingmei2.sample.main.home.HomeFragment
import com.qingmei2.sample.main.profile.ProfileFragment
import com.qingmei2.sample.main.task.TaskFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

val mainKodeinModule = Kodein.Module(MAIN_MODULE_TAG) {

    bind<HomeFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        HomeFragment()
    }

    bind<TaskFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        TaskFragment()
    }

    bind<ProfileFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        ProfileFragment()
    }

    bind<MainNavigator>() with scoped(AndroidComponentsWeakScope).singleton {
        MainNavigator(instance(), instance<MainActivity>().navigation)
    }

    bind<NavHostFragment>() with scoped(AndroidComponentsWeakScope).singleton {
        instance<MainActivity>().navHostFragment as NavHostFragment
    }

    bind<MainViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        instance<MainActivity>().viewModel(MainViewModel::class.java)
    }
}