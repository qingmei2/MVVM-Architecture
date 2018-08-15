package com.qingmei2.rhine.base

import android.app.Application

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() {
        BaseApplication.instance = this
    }

    companion object {

        var instance: BaseApplication? = null
            private set
    }
}
