package com.qingmei2.library.base;

import android.app.Activity;
import android.app.Application;
import android.support.v4.content.ContextCompat;

import com.qingmei2.library.di.module.AppModule;
import com.qingmei2.library.di.module.CacheModule;
import com.qingmei2.library.di.module.GlobalConfigModule;
import com.qingmei2.library.di.module.HttpClientModule;
import com.qingmei2.library.di.module.ServiceModule;
import com.qingmei2.library.http.Api;
import com.qingmei2.library.http.base.interceptor.HttpRequestHandler;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class BaseApplication extends Application implements HasActivityInjector{
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
    }

    private void inject() {
        BaseApplication.instance = this;
        DaggerAppComponent.builder()
                .cacheModule(getCacheModule())
                .appModule(getAppModule())                      //注入application
                .globalConfigModule(getGlobalConfigModule())    //注入全局配置
                .httpClientModule(getHttpClientModule())        //注入http配置
                .serviceModule(getServiceModule())              //注入所有Service
                .build()
                .inject(this);
    }

    private AppModule getAppModule() {
        return new AppModule(this);
    }

    private GlobalConfigModule getGlobalConfigModule() {
        return GlobalConfigModule.buidler()
                .baseurl(Api.BASE_API)
                //这行代码为log打印网络请求信息，可以考虑在release版中取消该行代码
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .globeHttpHandler(new HttpRequestHandler() {
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        return response;
                    }

                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        return request;
                    }
                })
                .build();
    }

    private HttpClientModule getHttpClientModule() {
        return new HttpClientModule();
    }

    private ServiceModule getServiceModule() {
        return new ServiceModule();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public CacheModule getCacheModule() {
        return new CacheModule(ContextCompat.getExternalCacheDirs(this)[0]);
    }
}
