package com.qingmei2.sample.ui.main.home

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import kotlinx.android.synthetic.main.fragment_home.*
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

    bind<FabAnimateViewModel>() with provider {
        FabAnimateViewModel().apply {
            addLifecycle(instance<HomeFragment>())
        }
    }

    bind<HomeViewDelegate>() with provider {
        HomeViewDelegate(
                homeViewModel = instance(),
                fabViewModel = instance(),
                fabTop = instance<HomeFragment>().fabTop,
                loadingDelegate = CommonLoadingViewModel.instance(instance<HomeFragment>())
        )
    }

    bind<IRemoteHomeDataSource>() with provider {
        HomeRemoteDataSource(instance())
    }

    bind<HomeRepository>() with provider {
        HomeRepository(instance())
    }
}