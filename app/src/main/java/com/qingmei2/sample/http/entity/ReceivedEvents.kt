package com.qingmei2.sample.http.entity

import com.google.gson.annotations.SerializedName

data class ReceivedEvent(val id: String,
                         val type: Type,
                         val actor: Actor,
//                         val payload: Payload,
                         val repo: Repo,
                         val public: Boolean,
                         @SerializedName("created_at")
                         val createdAt: String)

data class Actor(val id: Int,
                 val login: String,
                 @SerializedName("display_login")
                 val displayLogin: String,
                 @SerializedName("gravatar_id")
                 val gravatarId: String,
                 val url: String,
                 @SerializedName("avatar_url")
                 val avatarUrl: String)

data class Repo(val id: String,
                val name: String,
                val url: String)

data class Payload(val action: String?,
                   val forkee: String?)  // todo  ForkInfo


enum class Type {
    WatchEvent,
    ForkEvent,
    CreateEvent;
}