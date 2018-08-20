package com.qingmei2.rhine.base.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper

open class LifecycleViewModel : ViewModel(), ILifecycleViewModel {

    var lifecycleOwner: LifecycleOwner? = null

    override fun initLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
    }

    override fun checkLifecycleOwner(): Boolean = lifecycleOwner != null

    override fun releaseLifecycleOwner() {
        this.lifecycleOwner = null
    }

    @CallSuper
    override fun onCreate(lifecycleOwner: LifecycleOwner) {

    }

    @CallSuper
    override fun onStart(lifecycleOwner: LifecycleOwner) {

    }

    @CallSuper
    override fun onResume(lifecycleOwner: LifecycleOwner) {

    }

    @CallSuper
    override fun onPause(lifecycleOwner: LifecycleOwner) {

    }

    @CallSuper
    override fun onStop(lifecycleOwner: LifecycleOwner) {

    }

    @CallSuper
    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        releaseLifecycleOwner()
    }

    @CallSuper
    override fun onLifecycleChanged(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {

    }
}