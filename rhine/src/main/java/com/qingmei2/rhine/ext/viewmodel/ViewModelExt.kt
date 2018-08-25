package com.qingmei2.rhine.ext.viewmodel

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel

fun <T : LifecycleViewModel> FragmentActivity.viewModel(modelClass: Class<T>) =
        ViewModelProviders.of(this).get(modelClass).also {
            lifecycle.addObserver(it)
        }

fun <T : LifecycleViewModel> Fragment.viewModel(modelClass: Class<T>) =
        ViewModelProviders.of(activity!!).get(modelClass).also {
            lifecycle.addObserver(it)
        }