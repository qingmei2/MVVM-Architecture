package com.qingmei2.sample.http.service

import com.qingmei2.sample.entity.UserAccessToken
import com.qingmei2.sample.http.service.bean.LoginRequestModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {

    @POST("authorizations")
    @Headers("Accept: application/json")
    suspend fun authorizations(@Body authRequestModel: LoginRequestModel): Response<UserAccessToken>
}