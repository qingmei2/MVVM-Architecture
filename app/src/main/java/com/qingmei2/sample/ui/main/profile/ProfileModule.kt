package com.qingmei2.sample.ui.main.profile

import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val PROFILE_MODULE_TAG = "PROFILE_MODULE_TAG"

val profileKodeinModule = Kodein.Module(PROFILE_MODULE_TAG) {

    bind<ProfileViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        ProfileViewDelegate()
    }
}