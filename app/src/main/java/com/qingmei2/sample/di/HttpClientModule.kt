package com.qingmei2.sample.di

import com.qingmei2.sample.BuildConfig
import com.qingmei2.sample.http.interceptor.BasicAuthInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val HTTP_CLIENT_MODULE_TAG = "httpClientModule"
const val HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG = "http_client_module_interceptor_log_tag"
const val HTTP_CLIENT_MODULE_INTERCEPTOR_AUTH_TAG = "http_client_module_interceptor_auth_tag"

const val TIME_OUT_SECONDS = 10
const val BASE_URL = "https://api.github.com/"

val httpClientModule = Kodein.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit.Builder>() with provider { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with provider { OkHttpClient.Builder() }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
                .baseUrl(BASE_URL)
                .client(instance())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    bind<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG) with singleton {
        HttpLoggingInterceptor().apply {
            level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                false -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    bind<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_AUTH_TAG) with singleton {
        BasicAuthInterceptor(mUserInfoRepository = instance())
    }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
                .connectTimeout(
                        TIME_OUT_SECONDS.toLong(),
                        TimeUnit.SECONDS)
                .readTimeout(
                        TIME_OUT_SECONDS.toLong(),
                        TimeUnit.SECONDS)
                .addInterceptor(instance<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG))
                .addInterceptor(instance<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_AUTH_TAG))
                .build()
    }
}