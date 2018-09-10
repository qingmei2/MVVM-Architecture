package com.qingmei2.sample.http.service

import com.qingmei2.sample.di.TIME_OUT_SECONDS
import com.qingmei2.sample.http.APIConstants
import com.qingmei2.sample.http.entity.LoginUser
import com.qingmei2.sample.http.interceptor.BasicAuthInterceptor
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class LoginServiceImpl(private val httpInterceptor: Interceptor) {

    init {

    }

    fun login(username: String,
              password: String): Flowable<LoginUser> {

        val client =
                OkHttpClient.Builder()
                        .connectTimeout(
                                TIME_OUT_SECONDS.toLong(),
                                TimeUnit.SECONDS)
                        .readTimeout(TIME_OUT_SECONDS.toLong(),
                                TimeUnit.SECONDS)
                        .addInterceptor(httpInterceptor)
                        .addInterceptor(BasicAuthInterceptor(username, password))
                        .build()

        val retrofit =
                Retrofit.Builder()
                        .baseUrl(APIConstants.BASE_API)
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        return retrofit.create(LoginService::class.java).login()
    }
}