package com.qingmei2.sample.ui.main.repos

import android.support.v4.app.Fragment
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.common.FabAnimateViewModel
import kotlinx.android.synthetic.main.fragment_repos.*
import org.kodein.di.Kodein
import org.kodein.di.android.support.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val REPOS_MODULE_TAG = "REPOS_MODULE_TAG"

val reposKodeinModule = Kodein.Module(REPOS_MODULE_TAG) {

    bind<FabAnimateViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        FabAnimateViewModel().apply {
            addLifecycle(context)
        }
    }

    bind<ReposViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ReposViewModel.instance(
                activity = context.activity!!,
                repo = instance()
        )
    }

    bind<ReposViewDelegate>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ReposViewDelegate(instance(), instance(), (context as ReposFragment).fabTop)
    }

    bind<ILocalReposDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        LocalReposDataSource()
    }

    bind<IRemoteReposDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        RemoteReposDataSource(instance())
    }

    bind<ReposDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ReposDataSource(instance(), instance())
    }
}