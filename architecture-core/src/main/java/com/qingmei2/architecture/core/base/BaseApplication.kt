package com.qingmei2.architecture.core.base

import android.app.Application

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    open fun isTestEnvironment(): Boolean {
        return false
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}
