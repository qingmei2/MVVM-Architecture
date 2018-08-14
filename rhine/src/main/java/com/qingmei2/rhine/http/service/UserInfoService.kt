package com.qingmei2.rhine.http.service


import com.qingmei2.rhine.http.entity.UserInfo

import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by QingMei on 2017/8/15.
 * desc:
 */

interface UserInfoService {

    @GET("users/{user}")
    fun getUserInfo(@Path("user") user: String): Maybe<UserInfo>

}
