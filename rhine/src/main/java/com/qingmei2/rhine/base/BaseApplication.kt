package com.qingmei2.rhine.base

import android.app.Activity
import android.app.Application
import android.support.v4.content.ContextCompat

import com.qingmei2.rhine.di.component.DaggerAppComponent
import com.qingmei2.rhine.di.module.AppModule
import com.qingmei2.rhine.di.module.CacheModule
import com.qingmei2.rhine.di.module.GlobalConfigModule
import com.qingmei2.rhine.di.module.HttpClientModule
import com.qingmei2.rhine.di.module.ServiceModule
import com.qingmei2.rhine.http.Api
import com.qingmei2.rhine.http.base.interceptor.HttpRequestHandler

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

open class BaseApplication : Application(), HasActivityInjector {
    @Inject
    internal var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    private val appModule: AppModule
        get() = AppModule(this)

    private//这行代码为log打印网络请求信息，可以考虑在release版中取消该行代码
    val globalConfigModule: GlobalConfigModule
        get() = GlobalConfigModule.buidler()
                .baseurl(Api.BASE_API)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .globeHttpHandler(object : HttpRequestHandler {
                    override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response {
                        return response
                    }

                    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                        return request
                    }
                })
                .build()

    private val httpClientModule: HttpClientModule
        get() = HttpClientModule()

    private val serviceModule: ServiceModule
        get() = ServiceModule()

    val cacheModule: CacheModule
        get() = CacheModule(ContextCompat.getExternalCacheDirs(this)[0])

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() {
        BaseApplication.instance = this
        DaggerAppComponent.builder()
                .cacheModule(cacheModule)
                .appModule(appModule)                      //注入application
                .globalConfigModule(globalConfigModule)    //注入全局配置
                .httpClientModule(httpClientModule)        //注入http配置
                .serviceModule(serviceModule)              //注入所有Service
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    companion object {

        var instance: BaseApplication? = null
            private set
    }
}
