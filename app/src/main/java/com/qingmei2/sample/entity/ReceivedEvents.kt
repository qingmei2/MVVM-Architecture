package com.qingmei2.sample.entity

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "user_received_events"
)
@TypeConverters(
        TypeEnumConverter::class,
        ReceivedEventActorConverter::class,
        ReceivedEventRepoConverter::class
)
data class ReceivedEvent(
        @PrimaryKey
        val id: Long,
        @ColumnInfo(name = "type")
        val type: Type,
        @ColumnInfo(name = "actor")
        val actor: Actor,
        @ColumnInfo(name = "repo")
        val repo: ReceivedEventRepo,
        @SerializedName("public")
        @ColumnInfo(name = "is_public")
        val isPublic: Boolean,
        @SerializedName("created_at")
        @ColumnInfo(name = "created_at")
        val createdAt: String?
) {
    var indexInResponse: Int = -1
}

data class Actor(
        @SerializedName("id")
        val actorId: Int,
        val login: String,
        @SerializedName("display_login")
        val displayLogin: String,
        @SerializedName("gravatar_id")
        val gravatarId: String,
        val url: String,
        @SerializedName("avatar_url")
        val avatarUrl: String
)

data class ReceivedEventRepo(
        @SerializedName("id")
        val repoId: String,
        val name: String,
        val url: String
)

enum class Type {
    WatchEvent,
    ForkEvent,
    PushEvent,
    CreateEvent,
    MemberEvent,
    PublicEvent,
    IssuesEvent,
    IssueCommentEvent,
    CheckRunEvent,
    CheckSuiteEvent,
    CommitCommentEvent,
    DeleteEvent,
    DeploymentEvent,
    DeploymentStatusEvent,
    DownloadEvent,
    FollowEvent,
    ForkApplyEvent,
    GitHubAppAuthorizationEvent,
    GistEvent,
    GollumEvent,
    InstallationEvent,
    InstallationRepositoriesEvent,
    MarketplacePurchaseEvent,
    LabelEvent,
    MembershipEvent,
    MilestoneEvent,
    OrganizationEvent,
    OrgBlockEvent,
    PageBuildEvent,
    ProjectCardEvent,
    ProjectColumnEvent,
    ProjectEvent,
    PullRequestEvent,
    PullRequestReviewEvent,
    PullRequestReviewCommentEvent,
    ReleaseEvent,
    RepositoryEvent,
    RepositoryImportEvent,
    RepositoryVulnerabilityAlertEvent,
    SecurityAdvisoryEvent,
    StatusEvent,
    TeamEvent,
    TeamAddEvent
}

val DISPLAY_EVENT_TYPES: List<Type> = listOf(
        Type.WatchEvent,
        Type.ForkEvent,
        Type.PushEvent,
        Type.CreateEvent
)

class TypeEnumConverter {

    @TypeConverter
    fun restoreEnum(enumName: String): Type = Type.valueOf(enumName)

    @TypeConverter
    fun saveEnumToString(enumType: Type) = enumType.name
}

class ReceivedEventActorConverter {

    @TypeConverter
    fun storeActorToString(data: Actor): String =
            Gson().toJson(data)

    @TypeConverter
    fun storeStringToActor(value: String): Actor =
            Gson().fromJson(value, Actor::class.java)
}

class ReceivedEventRepoConverter {

    @TypeConverter
    fun storeRepoToString(data: ReceivedEventRepo): String =
            Gson().toJson(data)

    @TypeConverter
    fun storeStringToRepo(value: String): ReceivedEventRepo =
            Gson().fromJson(value, ReceivedEventRepo::class.java)
}