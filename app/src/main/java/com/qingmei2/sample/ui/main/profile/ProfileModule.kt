package com.qingmei2.sample.ui.main.profile

import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.generic.*

const val PROFILE_MODULE_TAG = "PROFILE_MODULE_TAG"

val profileKodeinModule = Kodein.Module(PROFILE_MODULE_TAG) {

    bind<ProfileViewDelegate>() with scoped(AndroidComponentsWeakScope).singleton {
        ProfileViewDelegate(instance())
    }

    bind<ProfileViewModel>() with scoped(AndroidComponentsWeakScope).singleton {
        ProfileViewModel(instance()).apply {
            addLifecycle(instance<ProfileFragment>())
        }
    }

    bind<ProfileRemoteDataSource>() with provider {
        ProfileRemoteDataSource(instance())
    }

    bind<ProfileRepository>() with provider {
        ProfileRepository(instance())
    }
}