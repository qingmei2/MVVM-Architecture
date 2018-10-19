package com.qingmei2.rhine.logger

import android.app.Application
import timber.log.Timber

fun Application.initLogger(isDebug: Boolean = true) {
    if (isDebug)
        Timber.plant(Timber.DebugTree())
    else
        Timber.plant(CrashReportingTree())
}