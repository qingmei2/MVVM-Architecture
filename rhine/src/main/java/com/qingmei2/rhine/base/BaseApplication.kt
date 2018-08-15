package com.qingmei2.rhine.base

import android.app.Application
import android.content.Context
import com.qingmei2.rhine.di.HttpClientModule
import com.qingmei2.rhine.di.cacheModule
import com.qingmei2.rhine.di.rxModule
import com.qingmei2.rhine.di.serviceModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

open class BaseApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { this@BaseApplication }
        import(androidModule(this@BaseApplication))

        import(serviceModule)
        import(cacheModule)
        import(rxModule)
        import(HttpClientModule)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}
