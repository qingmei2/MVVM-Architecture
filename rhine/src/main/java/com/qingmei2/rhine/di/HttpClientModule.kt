package com.qingmei2.rhine.di

import com.google.gson.Gson
import com.qingmei2.rhine.http.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val HTTP_CLIENT_MODULE_TAG = "HttpClientModule"

const val TIME_OUT_SECONDS = 20

val HttpClientModule = Kodein.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with singleton { OkHttpClient.Builder() }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(Api.BASE_API)
                .client(instance())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
                .connectTimeout(
                        TIME_OUT_SECONDS.toLong(),
                        TimeUnit.SECONDS)
                .readTimeout(
                        TIME_OUT_SECONDS.toLong(),
                        TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
    }

    bind<Gson>() with singleton { Gson() }
}