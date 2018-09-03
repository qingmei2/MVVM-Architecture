package com.qingmei2.rhine.http.service

import com.qingmei2.rhine.http.entity.LoginUser
import io.reactivex.Flowable
import retrofit2.http.GET

interface LoginService {

    @GET("user")
    fun login(): Flowable<LoginUser>
}