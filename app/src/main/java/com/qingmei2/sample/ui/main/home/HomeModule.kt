package com.qingmei2.sample.ui.main.home

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(fragment = context, repo = instance())
    }

    bind<HomeRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRemoteDataSource(serviceManager = instance())
    }

    bind<HomeLocalDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeLocalDataSource(db = instance())
    }

    bind<HomeRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRepository(remoteDataSource = instance(), localDataSource = instance())
    }
}