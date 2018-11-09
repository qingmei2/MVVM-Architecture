package com.qingmei2.sample.ui.main

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.ui.main.home.HomeFragment
import com.qingmei2.sample.ui.main.profile.ProfileFragment
import com.qingmei2.sample.ui.main.repos.ReposFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein.Module
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

const val MAIN_LIST_FRAGMENT = "MAIN_LIST_FRAGMENT"

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

    bind<BottomNavigationView>() with scoped(AndroidComponentsWeakScope).singleton {
        instance<MainFragment>().navigation
    }

    bind<ViewPager>() with scoped(AndroidComponentsWeakScope).singleton {
        instance<MainFragment>().viewPager
    }

    bind<List<Fragment>>(MAIN_LIST_FRAGMENT) with scoped(AndroidComponentsWeakScope).singleton {
        listOf<Fragment>(instance<HomeFragment>(), instance<ReposFragment>(), instance<ProfileFragment>())
    }

    bind<MainViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        MainViewDelegate(instance(), instance(), instance(MAIN_LIST_FRAGMENT), instance(), instance(), instance())
    }
}