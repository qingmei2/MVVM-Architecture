package com.qingmei2.rhine.http.interceptor

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(val username: String,
                           val password: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val basic = "$username:$password".let {
            "basic " + Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP)
        }

        val request = chain.request().let {
            it.newBuilder()
                    .header("Authorization", basic)
                    .method(it.method(), it.body())
                    .build()
        }
        return chain.proceed(request)
    }
}