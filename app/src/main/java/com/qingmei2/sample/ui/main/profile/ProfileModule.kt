package com.qingmei2.sample.ui.main.profile

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val PROFILE_MODULE_TAG = "PROFILE_MODULE_TAG"

val profileKodeinModule = Kodein.Module(PROFILE_MODULE_TAG) {

    bind<ProfileViewDelegate>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileViewDelegate(instance())
    }

    bind<ProfileViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileViewModel.instance(
                activity = context.activity!!,
                repo = instance()
        )
    }

    bind<ProfileRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileRemoteDataSource(instance())
    }

    bind<ProfileRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ProfileRepository(instance())
    }
}