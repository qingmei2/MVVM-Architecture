package com.qingmei2.sample.ui.main.repos

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val REPOS_MODULE_TAG = "REPOS_MODULE_TAG"

val reposKodeinModule = Kodein.Module(REPOS_MODULE_TAG) {

    bind<ReposViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposViewModel.instance(
                fragment = context,
                repo = instance()
        )
    }

    bind<ReposRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ReposRepository(
                remote = RemoteReposDataSource(serviceManager = instance()),
                local = LocalReposDataSource(db = instance())
        )
    }
}