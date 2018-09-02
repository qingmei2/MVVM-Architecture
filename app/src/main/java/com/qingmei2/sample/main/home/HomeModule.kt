package com.qingmei2.sample.main.home

import com.qingmei2.rhine.ext.viewmodel.viewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeViewModel>() with provider {
        instance<HomeFragment>().viewModel(HomeViewModel::class.java)
    }

    bind<HomeViewDelegate>() with provider {
        HomeViewDelegate(instance())
    }
}