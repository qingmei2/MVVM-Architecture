package com.qingmei2.sample.ui.main.home

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.ui.main.home.data.HomeRemoteDataSource
import com.qingmei2.sample.ui.main.home.data.HomeRepository
import com.qingmei2.sample.ui.main.home.data.IRemoteHomeDataSource
import com.qingmei2.sample.ui.main.home.presentation.HomeFragment
import com.qingmei2.sample.ui.main.home.presentation.HomeViewDelegate
import com.qingmei2.sample.ui.main.home.presentation.HomeViewModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeViewModel>() with provider {
        HomeViewModel(instance()).apply {
            addLifecycle(instance<HomeFragment>())
        }
    }

    bind<HomeViewDelegate>() with provider {
        HomeViewDelegate(instance())
    }

    bind<IRemoteHomeDataSource>() with provider {
        HomeRemoteDataSource(instance())
    }

    bind<HomeRepository>() with provider {
        HomeRepository(instance())
    }
}