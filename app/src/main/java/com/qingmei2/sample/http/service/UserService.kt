package com.qingmei2.sample.http.service

import com.qingmei2.sample.entity.ReceivedEvent
import com.qingmei2.sample.entity.Repo
import com.qingmei2.sample.entity.SearchResult
import com.qingmei2.sample.entity.UserInfo
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @GET("user")
    suspend fun fetchUserOwner(@Header("Authorization") authorization: String): Response<UserInfo>

    @GET("users/{username}/received_events?")
    suspend fun queryReceivedEvents(@Path("username") username: String,
                                    @Query("page") pageIndex: Int,
                                    @Query("per_page") perPage: Int): List<ReceivedEvent>

    @GET("users/{username}/repos?")
    suspend fun queryRepos(@Path("username") username: String,
                           @Query("page") pageIndex: Int,
                           @Query("per_page") perPage: Int,
                           @Query("sort") sort: String): List<Repo>

    @GET("search/repositories")
    suspend fun search(@Query("q") q: String,
                       @Query("page") pageIndex: Int,
                       @Query("per_page") perPage: Int): SearchResult

}
