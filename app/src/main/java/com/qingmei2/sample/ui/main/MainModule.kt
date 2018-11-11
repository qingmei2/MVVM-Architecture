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
import org.kodein.di.android.support.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

const val MAIN_LIST_FRAGMENT = "MAIN_LIST_FRAGMENT"

val mainKodeinModule = Module(MAIN_MODULE_TAG) {

    bind<HomeFragment>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        HomeFragment()
    }

    bind<ReposFragment>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ReposFragment()
    }

    bind<ProfileFragment>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ProfileFragment()
    }

    bind<MainNavigator>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        MainNavigator()
    }

    bind<MainViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        MainViewModel().apply {
            addLifecycle(context)
        }
    }

    bind<BottomNavigationView>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        (context as MainFragment).navigation
    }

    bind<ViewPager>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        (context as MainFragment).viewPager
    }

    bind<List<Fragment>>(MAIN_LIST_FRAGMENT) with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        listOf<Fragment>(instance<HomeFragment>(), instance<ReposFragment>(), instance<ProfileFragment>())
    }

    bind<MainViewDelegate>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        MainViewDelegate(instance(), instance(), instance(MAIN_LIST_FRAGMENT), instance(), instance(), instance())
    }
}