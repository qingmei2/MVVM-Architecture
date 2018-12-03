package com.qingmei2.sample.ui.main.home

import android.support.v4.app.Fragment
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.common.FabAnimateViewModel
import com.qingmei2.sample.common.loadings.CommonLoadingViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.Kodein
import org.kodein.di.android.support.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val HOME_MODULE_TAG = "HOME_MODULE_TAG"

val homeKodeinModule = Kodein.Module(HOME_MODULE_TAG) {

    bind<HomeViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        HomeViewModel.instance(
                activity = context.activity!!,
                repo = instance()
        )
    }

    bind<FabAnimateViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        FabAnimateViewModel().apply {
            addLifecycle(context)
        }
    }

    bind<HomeViewDelegate>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        HomeViewDelegate(
                homeViewModel = instance(),
                fabViewModel = instance(),
                fabTop = (context as HomeFragment).fabTop,
                loadingDelegate = CommonLoadingViewModel.instance(context)
        )
    }

    bind<IRemoteHomeDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        HomeRemoteDataSource(instance())
    }

    bind<HomeRepository>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        HomeRepository(instance())
    }
}