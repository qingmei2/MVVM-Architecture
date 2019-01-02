package com.qingmei2.sample.ui.main.home

import androidx.fragment.app.Fragment
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeViewModel.instance(
                fragment = context,
                repo = instance()
        )
    }

    bind<FabAnimateViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        FabAnimateViewModel()
    }

    bind<CommonLoadingViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        CommonLoadingViewModel.instance()
    }

    bind<IRemoteHomeDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRemoteDataSource(instance())
    }

    bind<HomeRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        HomeRepository(instance())
    }
}