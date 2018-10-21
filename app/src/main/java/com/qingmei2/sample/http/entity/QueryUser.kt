package com.qingmei2.sample.http.entity

import com.google.gson.annotations.SerializedName

data class QueryUser(val name: String,
                     val login: String,
                     @SerializedName("avatar_url") val avatarUrl: String,
                     val bio: String,
                     val location: String)
