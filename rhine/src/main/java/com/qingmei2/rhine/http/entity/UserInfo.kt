package com.qingmei2.rhine.http.entity

import com.google.gson.annotations.SerializedName

data class UserInfo(val name: String,
                    val login: String,
                    @SerializedName("avatar_url")
                    val avatarUrl: String)
