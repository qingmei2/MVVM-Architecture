package com.qingmei2.rhine.http.service

import com.qingmei2.rhine.http.entity.UserInfo
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{username}")
    fun fetchUserInfo(@Path("username") username: String): Flowable<UserInfo>
}
