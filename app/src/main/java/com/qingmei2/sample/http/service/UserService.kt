package com.qingmei2.sample.http.service

import com.qingmei2.sample.http.entity.ReceivedEvent
import com.qingmei2.sample.http.entity.Repo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{username}/received_events")
    fun queryReceivedEvents(@Path("username") username: String): Flowable<List<ReceivedEvent>>

    @GET("users/{username}/repos")
    fun queryRepos(@Path("username") username: String): Flowable<List<Repo>>
}