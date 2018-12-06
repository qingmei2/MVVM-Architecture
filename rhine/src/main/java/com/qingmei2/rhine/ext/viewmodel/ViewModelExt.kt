package com.qingmei2.rhine.ext.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel

fun LifecycleViewModel.addLifecycle(activity: androidx.fragment.app.FragmentActivity) =
        activity inject this

fun LifecycleViewModel.addLifecycle(fragment: androidx.fragment.app.Fragment) =
        fragment inject this

fun LifecycleViewModel.addLifecycle(lifecycleOwner: LifecycleOwner) =
        lifecycleOwner inject this

private infix fun <A : LifecycleOwner, B : LifecycleViewModel> A.inject(viewModel: B) =
        lifecycle.addObserver(viewModel)
