package com.qingmei2.rhine.base

import android.arch.lifecycle.LifecycleOwner

interface IView {

    fun injectLifecycleOwner(lifecycleOwner: LifecycleOwner)
}