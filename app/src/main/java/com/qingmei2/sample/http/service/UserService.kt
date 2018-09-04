package com.qingmei2.sample.http.service

import com.qingmei2.sample.http.entity.QueryUser
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{username}")
    fun queryUser(@Path("username") username: String): Flowable<QueryUser>
}