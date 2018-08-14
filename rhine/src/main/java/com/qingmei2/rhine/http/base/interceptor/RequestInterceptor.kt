package com.qingmei2.rhine.http.base.interceptor

import java.io.IOException

import javax.inject.Inject
import javax.inject.Singleton

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by Glooory on 17/5/15.
 */
@Singleton
class RequestInterceptor @Inject
constructor(private val mHttpRequestHandler: HttpRequestHandler?) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        mHttpRequestHandler?.onHttpRequestBefore(chain, request)

        val originalResponse = chain.proceed(request)
        val responseBody = originalResponse.body()

        mHttpRequestHandler?.onHttpResultResponse(responseBody!!.toString(), chain, originalResponse)

        return originalResponse
    }
}
