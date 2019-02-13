package com.qingmei2.sample.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.qingmei2.sample.ui.main.home.HomeFragment
import com.qingmei2.sample.ui.main.profile.ProfileFragment
import com.qingmei2.sample.ui.main.repos.ReposFragment
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.Kodein.Module
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

const val MAIN_LIST_FRAGMENT = "MAIN_LIST_FRAGMENT"

val mainKodeinModule = Module(MAIN_MODULE_TAG) {

    bind<HomeFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeFragment()
    }

    bind<ReposFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposFragment()
    }

    bind<ProfileFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileFragment()
    }

    bind<MainViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        MainViewModel.instance(context)
    }

    bind<BottomNavigationView>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        (context as MainFragment).navigation
    }

    bind<ViewPager>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        (context as MainFragment).viewPager
    }

    bind<List<Fragment>>(MAIN_LIST_FRAGMENT) with scoped<Fragment>(AndroidLifecycleScope).singleton {
        listOf<Fragment>(instance<HomeFragment>(), instance<ReposFragment>(), instance<ProfileFragment>())
    }
}