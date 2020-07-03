package com.qingmei2.sample.base

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.qingmei2.architecture.core.logger.initLogger
import com.qingmei2.sample.BuildConfig
import com.qingmei2.sample.di.*
import com.squareup.leakcanary.LeakCanary
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        initLogger(BuildConfig.DEBUG)
        initStetho()
        initLeakCanary()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}
