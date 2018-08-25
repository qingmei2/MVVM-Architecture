package com.qingmei2.rhine.ext.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

fun <T : ViewModel> FragmentActivity.viewModel(modelClass: Class<T>) =
        ViewModelProviders.of(this).get(modelClass)

fun <T : ViewModel> Fragment.viewModel(modelClass: Class<T>) =
        ViewModelProviders.of(activity!!).get(modelClass)