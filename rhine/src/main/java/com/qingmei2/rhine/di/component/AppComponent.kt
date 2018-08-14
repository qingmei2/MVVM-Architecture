package com.qingmei2.rhine.di.component

import com.google.gson.Gson
import com.qingmei2.rhine.base.BaseApplication
import com.qingmei2.rhine.di.module.ActivitiesModule
import com.qingmei2.rhine.di.module.AppModule
import com.qingmei2.rhine.di.module.CacheModule
import com.qingmei2.rhine.di.module.GlobalConfigModule
import com.qingmei2.rhine.di.module.HttpClientModule
import com.qingmei2.rhine.di.module.ServiceModule
import com.qingmei2.rhine.di.scheduler.SchedulersModule
import com.qingmei2.rhine.http.service.ServiceManager

import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient

/**
 * Created by QingMei on 2017/8/14.
 * desc:
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AndroidSupportInjectionModule::class, AppModule::class, HttpClientModule::class, GlobalConfigModule::class, ServiceModule::class, CacheModule::class, ActivitiesModule::class, SchedulersModule::class))
interface AppComponent {

    fun gson(): Gson

    fun serviceManager(): ServiceManager

    fun okHttpClient(): OkHttpClient

    fun baseApplication(): BaseApplication

    fun inject(application: BaseApplication)
}
