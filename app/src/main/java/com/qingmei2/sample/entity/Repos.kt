package com.qingmei2.sample.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.qingmei2.sample.utils.fromJson
import com.qingmei2.sample.utils.toJson

@Entity(tableName = "user_repos")
@TypeConverters(ReposPersistentConverter::class)
data class Repo(
        @PrimaryKey
        val id: Long,
        @SerializedName("node_id")
        @ColumnInfo(name = "node_id")
        val nodeId: String,
        val name: String,
        @SerializedName("full_name")
        @ColumnInfo(name = "full_name")
        val fullName: String,
        @ColumnInfo(name = "is_private")
        @SerializedName("private")
        val isPrivate: Boolean,
        val owner: RepoOwner,
        @SerializedName("html_url")
        @ColumnInfo(name = "html_url")
        val htmlUrl: String,
        val description: String?,
        val fork: Boolean,
        val url: String,
        @SerializedName("forks_url")
        @ColumnInfo(name = "forks_url")
        val forksUrl: String,
        @SerializedName("keys_url")
        @ColumnInfo(name = "keys_url")
        val keysUrl: String,
        @SerializedName("collaborators_url")
        @ColumnInfo(name = "collaborators_url")
        val collaboratorsUrl: String,
        @SerializedName("teams_url")
        @ColumnInfo(name = "teams_url")
        val teamsUrl: String,
        @SerializedName("hooks_url")
        @ColumnInfo(name = "hooks_url")
        val hooksUrl: String,
        @SerializedName("issue_events_url")
        @ColumnInfo(name = "issue_events_url")
        val issueEventsUrl: String,
        @SerializedName("events_url")
        @ColumnInfo(name = "events_url")
        val eventsUrl: String,
        @SerializedName("assignees_url")
        @ColumnInfo(name = "assignees_url")
        val assigneesUrl: String,
        @SerializedName("branches_url")
        @ColumnInfo(name = "branches_url")
        val branchesUrl: String,
        @SerializedName("tags_url")
        @ColumnInfo(name = "tags_url")
        val tagsUrl: String,
        @SerializedName("blobs_url")
        @ColumnInfo(name = "blobs_url")
        val blobsUrl: String,
        @SerializedName("git_tags_url")
        @ColumnInfo(name = "git_tags_url")
        val gitTagsUrl: String,
        @SerializedName("git_refs_url")
        @ColumnInfo(name = "git_refs_url")
        val gitRefsUrl: String,
        @SerializedName("trees_url")
        @ColumnInfo(name = "trees_url")
        val treesUrl: String,
        @SerializedName("statuses_url")
        @ColumnInfo(name = "statuses_url")
        val statusesUrl: String,
        @SerializedName("languages_url")
        @ColumnInfo(name = "languages_url")
        val languagesUrl: String,
        @SerializedName("stargazers_url")
        @ColumnInfo(name = "stargazers_url")
        val stargazersUrl: String,
        @SerializedName("contributors_url")
        @ColumnInfo(name = "contributors_url")
        val contributorsUrl: String,
        @SerializedName("subscribers_url")
        @ColumnInfo(name = "subscribers_url")
        val subscribersUrl: String,
        @SerializedName("subscription_url")
        @ColumnInfo(name = "subscription_url")
        val subscriptionUrl: String,
        @SerializedName("commits_url")
        @ColumnInfo(name = "commits_url")
        val commitsUrl: String,
        @SerializedName("git_commits_url")
        @ColumnInfo(name = "git_commits_url")
        val gitCommitsUrl: String,
        @SerializedName("comments_url")
        @ColumnInfo(name = "comments_url")
        val commentsUrl: String,
        @SerializedName("issue_comment_url")
        @ColumnInfo(name = "issue_comment_url")
        val issueCommentUrl: String,
        @SerializedName("contents_url")
        @ColumnInfo(name = "contents_url")
        val contentsUrl: String,
        @SerializedName("compare_url")
        @ColumnInfo(name = "compare_url")
        val compareUrl: String,
        @SerializedName("merges_url")
        @ColumnInfo(name = "merges_url")
        val mergesUrl: String,
        @SerializedName("archive_url")
        @ColumnInfo(name = "archive_url")
        val archiveUrl: String,
        @SerializedName("downloads_url")
        @ColumnInfo(name = "downloads_url")
        val downloadsUrl: String,
        @SerializedName("issues_url")
        @ColumnInfo(name = "issues_url")
        val issuesUrl: String,
        @SerializedName("pulls_url")
        @ColumnInfo(name = "pulls_url")
        val pullsUrl: String,
        @SerializedName("milestones_url")
        @ColumnInfo(name = "milestones_url")
        val milestonesUrl: String,
        @SerializedName("notifications_url")
        @ColumnInfo(name = "notifications_url")
        val notificationsUrl: String,
        @SerializedName("labels_url")
        @ColumnInfo(name = "labels_url")
        val labelsUrl: String,
        @SerializedName("releases_url")
        @ColumnInfo(name = "releases_url")
        val releasesUrl: String,
        @SerializedName("deployments_url")
        @ColumnInfo(name = "deployments_url")
        val deploymentsUrl: String,
        @SerializedName("created_at")
        @ColumnInfo(name = "created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        @ColumnInfo(name = "updated_at")
        val updatedAt: String,
        @SerializedName("pushed_at")
        @ColumnInfo(name = "pushed_at")
        val pushedAt: String,
        @SerializedName("git_url")
        @ColumnInfo(name = "git_url")
        val gitUrl: String,
        @SerializedName("ssh_url")
        @ColumnInfo(name = "ssh_url")
        val sshUrl: String,
        @SerializedName("clone_url")
        @ColumnInfo(name = "clone_url")
        val cloneUrl: String,
        @SerializedName("svn_url")
        @ColumnInfo(name = "svn_url")
        val svnUrl: String?,
        @SerializedName("homepage")
        @ColumnInfo(name = "homepage")
        val homepage: String?,
        val size: Int,
        @SerializedName("stargazers_count")
        @ColumnInfo(name = "stargazers_count")
        val stargazersCount: Int,
        @SerializedName("watchers_count")
        @ColumnInfo(name = "watchers_count")
        val watchersCount: Int,
        val language: String?,
        @SerializedName("has_issues")
        @ColumnInfo(name = "has_issues")
        val hasIssues: Boolean,
        @SerializedName("has_projects")
        @ColumnInfo(name = "has_projects")
        val hasProjects: Boolean,
        @SerializedName("has_downloads")
        @ColumnInfo(name = "has_downloads")
        val hasDownloads: Boolean,
        @SerializedName("has_wiki")
        @ColumnInfo(name = "has_wiki")
        val hasWiki: Boolean,
        @SerializedName("has_pages")
        @ColumnInfo(name = "has_pages")
        val hasPages: Boolean,
        @SerializedName("forks_count")
        @ColumnInfo(name = "forks_count")
        val forksCount: Int,
        @SerializedName("mirror_url")
        @ColumnInfo(name = "mirror_url")
        val mirrorUrl: String?,
        @SerializedName("open_issues_count")
        @ColumnInfo(name = "open_issues_count")
        val openIssuesCount: Int,
        val license: License?,
        val forks: Int,
        @SerializedName("open_issues")
        @ColumnInfo(name = "open_issues")
        val openIssues: Int,
        val watchers: Int,
        @ColumnInfo(name = "default_branch")
        val defaultBranch: String?
) {

    var indexInSortResponse: Int = -1   // persistent layer index
}

data class RepoOwner(
        val login: String,
        val id: Int,
        @SerializedName("node_id")
        val nodeId: String,
        @SerializedName("avatar_url")
        val avatarUrl: String,
        @SerializedName("gravatar_id")
        val gravatarId: String,
        val url: String,
        @SerializedName("html_url")
        val htmlUrl: String,
        @SerializedName("followers_url")
        val followersUrl: String,
        @SerializedName("following_url")
        val followingUrl: String,
        @SerializedName("gists_url")
        val gistsUrl: String,
        @SerializedName("starred_url")
        val starredUrl: String,
        @SerializedName("subscriptions_url")
        val subscriptionsUrl: String,
        @SerializedName("organizations_url")
        val organizationsUrl: String,
        @SerializedName("repos_url")
        val reposUrl: String,
        @SerializedName("events_url")
        val eventsUrl: String,
        @SerializedName("received_events_url")
        val receivedEventsUrl: String,
        val type: String,
        @SerializedName("site_admin") val siteAdmin: String
)

data class License(
        val key: String,
        val name: String,
        @SerializedName("spdx_id")
        val spdxId: String,
        val url: String,
        @SerializedName("node_id")
        val nodeId: String
)

class ReposPersistentConverter {

    // RepoOwner
    @TypeConverter
    fun storeRepoOwnerToString(data: RepoOwner): String = data.toJson()

    @TypeConverter
    fun storeStringToRepoOwner(value: String): RepoOwner = value.fromJson()

    // License
    @TypeConverter
    fun storeLicenseToString(data: License?): String = data.toJson()

    @TypeConverter
    fun storeStringToLicense(value: String): License? = value.fromJson()
}