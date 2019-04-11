package com.qingmei2.sample.http.service.bean

import com.google.gson.annotations.SerializedName
import com.qingmei2.sample.BuildConfig

data class LoginRequestModel(
        val scopes: List<String>,
        val note: String,
        @SerializedName("client_id")
        val clientId: String,
        @SerializedName("client_secret")
        val clientSecret: String
) {
    companion object {

        fun generate(): LoginRequestModel {
            return LoginRequestModel(
                    scopes = listOf("user", "repo", "gist", "notifications"),
                    note = BuildConfig.APPLICATION_ID,
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET
            )
        }
    }
}