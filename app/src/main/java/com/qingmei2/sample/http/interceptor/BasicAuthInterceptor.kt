package com.qingmei2.sample.http.interceptor

import android.util.Base64
import com.qingmei2.sample.repository.UserInfoRepository
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(
        private val mUserInfoRepository: UserInfoRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val accessToken = getAuthorization()

        if (accessToken.isNotEmpty()) {
            val url = request.url.toString()
            request = request.newBuilder()
                    .addHeader("Authorization", accessToken)
                    .url(url)
                    .build()
        }

        return chain.proceed(request)
    }

    private fun getAuthorization(): String {
        val accessToken = mUserInfoRepository.accessToken
        val username = mUserInfoRepository.username
        val password = mUserInfoRepository.password

        if (accessToken.isBlank()) {
            val basicIsEmpty = username.isBlank() || password.isBlank()
            return if (basicIsEmpty) {
                ""
            } else {
                "$username:$password".let {
                    "basic " + Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP)
                }
            }
        }
        return "token $accessToken"
    }
}