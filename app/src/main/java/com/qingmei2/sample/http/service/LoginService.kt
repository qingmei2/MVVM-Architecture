package com.qingmei2.sample.http.service

import com.qingmei2.sample.http.entity.LoginUser
import io.reactivex.Single
import retrofit2.http.GET

interface LoginService {

    @GET("user")
    fun login(): Single<LoginUser>
}