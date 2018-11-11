package com.qingmei2.rhine.base.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import com.qingmei2.rhine.logger.logd

open class LifecycleViewModel : ViewModel(), IViewModel {

    var lifecycleOwner: LifecycleOwner? = null

    @CallSuper
    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        logd { "$this onCreate()" }
        this.lifecycleOwner = lifecycleOwner
    }

    @CallSuper
    override fun onStart(lifecycleOwner: LifecycleOwner) {
        logd { "$this onstart()" }
    }

    @CallSuper
    override fun onResume(lifecycleOwner: LifecycleOwner) {
        logd { "$this onResume()" }
    }

    @CallSuper
    override fun onPause(lifecycleOwner: LifecycleOwner) {
        logd { "$this onPause()" }
    }

    @CallSuper
    override fun onStop(lifecycleOwner: LifecycleOwner) {
        logd { "$this onStop()" }
    }

    @CallSuper
    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        logd { "$this onDestroy()" }
        this.lifecycleOwner = null
    }

    @CallSuper
    override fun onLifecycleChanged(lifecycleOwner: LifecycleOwner,
                                    event: Lifecycle.Event) {

    }
}