package com.qingmei2.sample.entity

import com.google.gson.annotations.SerializedName

data class ReceivedEvent(val id: String,
                         val type: Type,
                         val actor: Actor,
//                         val payload: Payload,
                         val repo: ReceivedEventRepo,
                         val public: Boolean,
                         @SerializedName("created_at") val createdAt: String?)

data class Actor(val id: Int,
                 val login: String,
                 @SerializedName("display_login") val displayLogin: String,
                 @SerializedName("gravatar_id") val gravatarId: String,
                 val url: String,
                 @SerializedName("avatar_url") val avatarUrl: String)

data class ReceivedEventRepo(val id: String,
                             val name: String,
                             val url: String)

data class Payload(val action: String?,
                   val forkee: String?)


enum class Type {
    WatchEvent,
    ForkEvent,
    PushEvent,
    CreateEvent,

    MemberEvent,
    PublicEvent,
    IssuesEvent,
    IssueCommentEvent;
}

val DISPLAY_EVENT_TYPES: List<Type> = listOf(
        Type.WatchEvent,
        Type.ForkEvent,
        Type.PushEvent,
        Type.CreateEvent
)

val IGNORE_EVENT_TYPES: List<Type> = listOf(
        Type.MemberEvent,
        Type.PublicEvent,
        Type.IssuesEvent,
        Type.IssueCommentEvent
)