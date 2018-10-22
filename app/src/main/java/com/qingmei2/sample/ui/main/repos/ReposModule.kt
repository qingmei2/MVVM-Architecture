package com.qingmei2.sample.ui.main.repos

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import com.qingmei2.sample.ui.main.repos.data.*
import com.qingmei2.sample.ui.main.repos.presentation.ReposFragment
import com.qingmei2.sample.ui.main.repos.presentation.ReposViewDelegate
import com.qingmei2.sample.ui.main.repos.presentation.ReposViewModel
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val REPOS_MODULE_TAG = "REPOS_MODULE_TAG"

val reposKodeinModule = Kodein.Module(REPOS_MODULE_TAG) {

    bind<ReposViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        ReposViewDelegate(instance())
    }

    bind<ReposViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        ReposViewModel(instance()).apply {
            addLifecycle(instance<ReposFragment>())
        }
    }

    bind<ILocalReposDataSource>() with scoped(AndroidComponentsWeakScope).singleton {
        LocalReposDataSource()
    }

    bind<IRemoteReposDataSource>() with scoped(AndroidComponentsWeakScope).singleton {
        RemoteReposDataSource(instance())
    }

    bind<ReposDataSource>() with scoped(AndroidComponentsWeakScope).singleton {
        ReposDataSource(instance(), instance())
    }
}