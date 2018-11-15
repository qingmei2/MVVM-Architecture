package com.qingmei2.sample.http.service

import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.entity.Repo
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("users/{username}/received_events?")
    fun queryReceivedEvents(@Path("username") username: String,
                            @Query("page") pageIndex: Int,
                            @Query("per_page") perPage: Int): Flowable<List<ReceivedEvent>>

    @GET("users/{username}/repos?per_page=100")
    fun queryRepos(@Path("username") username: String): Flowable<List<Repo>>
}