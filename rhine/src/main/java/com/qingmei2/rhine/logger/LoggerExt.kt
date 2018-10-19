package com.qingmei2.rhine.logger

import android.app.Application
import timber.log.Timber

fun Application.initLogger(isDebug: Boolean = true) {
    if (isDebug)
        Timber.plant(Timber.DebugTree())
    else
        Timber.plant(CrashReportingTree())

    log { "initLogger successfully, isDebug = $isDebug" }
}

inline fun log(supplier: () -> String) = logd(supplier)

inline fun logd(supplier: () -> String) = Timber.d(supplier())

inline fun logi(supplier: () -> String) = Timber.i(supplier())

inline fun logw(supplier: () -> String) = Timber.w(supplier())

inline fun loge(supplier: () -> String) = Timber.e(supplier())