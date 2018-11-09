package com.qingmei2.rhine.ext.viewmodel

import android.arch.lifecycle.LifecycleOwner
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel

fun LifecycleViewModel.addLifecycle(activity: FragmentActivity) =
        activity inject this

fun LifecycleViewModel.addLifecycle(fragment: Fragment) =
        fragment inject this

fun LifecycleViewModel.addLifecycle(lifecycleOwner: LifecycleOwner) =
        lifecycleOwner inject this

private infix fun <A : LifecycleOwner, B : LifecycleViewModel> A.inject(viewModel: B) =
        lifecycle.addObserver(viewModel)
