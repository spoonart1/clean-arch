package com.spoonart1.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoryDto(
    val id: Long,
    @param:Json(name = "node_id") val nodeId: String,
    val name: String,
    @param:Json(name = "full_name") val fullName: String,
    val private: Boolean,
    val owner: OwnerDto,
    @param:Json(name = "html_url") val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val homepage: String?,
    val size: Int,
    @param:Json(name = "stargazers_count") val stargazersCount: Int,
    @param:Json(name = "watchers_count") val watchersCount: Int,
    val language: String?,
    @param:Json(name = "has_issues") val hasIssues: Boolean,
    @param:Json(name = "has_projects") val hasProjects: Boolean,
    @param:Json(name = "has_wiki") val hasWiki: Boolean,
    @param:Json(name = "has_pages") val hasPages: Boolean,
    @param:Json(name = "has_discussions") val hasDiscussions: Boolean,
    @param:Json(name = "forks_count") val forksCount: Int,
    val archived: Boolean,
    val disabled: Boolean,
    @param:Json(name = "open_issues_count") val openIssuesCount: Int,
    val license: LicenseDto?,
    @param:Json(name = "allow_forking") val allowForking: Boolean,
    @param:Json(name = "is_template") val isTemplate: Boolean,
    val topics: List<String>,
    val visibility: String,
    val forks: Int,
    @param:Json(name = "open_issues") val openIssues: Int,
    val watchers: Int,
    @param:Json(name = "default_branch") val defaultBranch: String,
    @param:Json(name = "created_at") val createdAt: String,
    @param:Json(name = "updated_at") val updatedAt: String,
    @param:Json(name = "pushed_at") val pushedAt: String?,
    val score: Double? = null
)
