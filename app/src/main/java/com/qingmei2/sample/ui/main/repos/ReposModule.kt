package com.qingmei2.sample.ui.main.repos

import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val REPOS_MODULE_TAG = "REPOS_MODULE_TAG"

val reposKodeinModule = Kodein.Module(REPOS_MODULE_TAG) {

    bind<ReposViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        ReposViewDelegate()
    }
}