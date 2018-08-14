package com.qingmei2.rhine.di.module

import com.google.gson.Gson
import com.qingmei2.rhine.http.base.interceptor.RequestInterceptor
import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by QingMei on 2017/8/15.
 * desc:
 */
@Module
class HttpClientModule {

    @Singleton
    @Provides
    internal fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient, httpUrl: HttpUrl): Retrofit {
        return builder
                .baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    internal fun provideClient(okHttpClient: OkHttpClient.Builder, intercept: Interceptor, interceptors: List<Interceptor>?): OkHttpClient {
        val builder = okHttpClient
                .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept)
        if (interceptors != null && interceptors.size > 0) {
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
        }
        return builder.build()
    }

    @Singleton
    @Provides
    internal fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    internal fun provideClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    internal fun provideIntercept(interceptor: RequestInterceptor): Interceptor {
        return interceptor
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    companion object {

        private val TIME_OUT_SECONDS = 20
    }

}
