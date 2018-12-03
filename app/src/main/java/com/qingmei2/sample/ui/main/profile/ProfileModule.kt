package com.qingmei2.sample.ui.main.profile

import android.support.v4.app.Fragment
import com.qingmei2.rhine.ext.viewmodel.addLifecycle
import org.kodein.di.Kodein
import org.kodein.di.android.AndroidComponentsWeakScope
import org.kodein.di.android.support.AndroidLifecycleScope
import org.kodein.di.generic.*

const val PROFILE_MODULE_TAG = "PROFILE_MODULE_TAG"

val profileKodeinModule = Kodein.Module(PROFILE_MODULE_TAG) {

    bind<ProfileViewDelegate>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ProfileViewDelegate(instance())
    }

    bind<ProfileViewModel>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ProfileViewModel.instance(
                activity = context.activity!!,
                repo = instance()
        )
    }

    bind<ProfileRemoteDataSource>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ProfileRemoteDataSource(instance())
    }

    bind<ProfileRepository>() with scoped(AndroidLifecycleScope<Fragment>()).singleton {
        ProfileRepository(instance())
    }
}