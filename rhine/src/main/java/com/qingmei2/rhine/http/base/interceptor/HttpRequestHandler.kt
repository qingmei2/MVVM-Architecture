package com.qingmei2.rhine.http.base.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Glooory on 17/5/15.
 */

interface HttpRequestHandler {

    fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response

    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request

    companion object {

        val EMPTY: HttpRequestHandler = object : HttpRequestHandler {
            override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response {
                return response
            }

            override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                return request
            }
        }
    }
}
