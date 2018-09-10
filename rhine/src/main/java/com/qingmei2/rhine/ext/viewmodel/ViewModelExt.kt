package com.qingmei2.rhine.ext.viewmodel

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.qingmei2.rhine.base.viewmodel.LifecycleViewModel

fun LifecycleViewModel.addLifecycle(activity: FragmentActivity) {
    activity.lifecycle.addObserver(this)
}

fun LifecycleViewModel.addLifecycle(fragment: Fragment) {
    fragment.lifecycle.addObserver(this)
}