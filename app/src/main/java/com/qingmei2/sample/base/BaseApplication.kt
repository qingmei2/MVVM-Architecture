package com.qingmei2.sample.base

import android.app.Application
import android.content.Context
import com.qingmei2.sample.di.httpClientModule
import com.qingmei2.sample.di.cacheModule
import com.qingmei2.sample.di.rxModule
import com.qingmei2.sample.di.serviceModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.support.androidSupportModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

open class BaseApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { this@BaseApplication }
        import(androidModule(this@BaseApplication))
        import(androidSupportModule(this@BaseApplication))

        import(serviceModule)
        import(cacheModule)
        import(rxModule)
        import(httpClientModule)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}
