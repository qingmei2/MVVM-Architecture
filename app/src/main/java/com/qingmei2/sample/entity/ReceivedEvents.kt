package com.qingmei2.sample.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.qingmei2.sample.utils.fromJson
import com.qingmei2.sample.utils.toJson

@TypeConverters(ReceivedEventsPersistentConverter::class)
@Entity(tableName = "user_received_events")
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
        val createdAt: String
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

val SUPPORT_EVENT_TYPES: List<Type> = listOf(
        Type.WatchEvent,
        Type.ForkEvent,
        Type.PushEvent,
        Type.CreateEvent
)

class ReceivedEventsPersistentConverter {

    // Actor
    @TypeConverter
    fun storeActorToString(data: Actor): String = data.toJson()

    @TypeConverter
    fun storeStringToActor(value: String): Actor = value.fromJson()

    // ReceivedEventRepo
    @TypeConverter
    fun storeRepoToString(data: ReceivedEventRepo): String = data.toJson()

    @TypeConverter
    fun storeStringToRepo(value: String): ReceivedEventRepo = value.fromJson()

    // Type
    @TypeConverter
    fun restoreEnum(enumName: String): Type = Type.valueOf(enumName)

    @TypeConverter
    fun saveEnumToString(enumType: Type) = enumType.name
}