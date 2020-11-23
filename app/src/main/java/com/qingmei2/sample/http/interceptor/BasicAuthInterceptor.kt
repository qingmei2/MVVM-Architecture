package com.qingmei2.sample.http.interceptor

import com.qingmei2.sample.BuildConfig
import com.qingmei2.sample.repository.UserInfoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(
        private val repository: UserInfoRepository
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
        val userPreferences = runBlocking {
            repository.fetchUserInfoFlow().first()
        }

        val accessToken = BuildConfig.USER_ACCESS_TOKEN
        val username = userPreferences.username
        val password = userPreferences.password

        if (accessToken.isBlank()) {
            val basicIsEmpty = username.isBlank() || password.isBlank()
            return if (basicIsEmpty) {
                ""
            } else {
                "$username:$password".let {
                    "basic " + android.util.Base64.encodeToString(it.toByteArray(), android.util.Base64.NO_WRAP)
                }
            }
        }
        return "token $accessToken"
    }
}