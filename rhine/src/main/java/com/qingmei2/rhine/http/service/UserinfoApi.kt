package com.qingmei2.rhine.http.service

import com.qingmei2.rhine.http.entity.UserInfo
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

interface UserinfoApi {

    @GET("users/{username}")
    fun fetchUserInfo(@Path("username") username: String): Maybe<UserInfo>
}
