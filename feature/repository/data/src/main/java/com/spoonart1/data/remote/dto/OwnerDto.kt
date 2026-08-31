package com.spoonart1.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerDto(
    val login: String,
    val id: Long,
    @param:Json(name = "node_id") val nodeId: String,
    @param:Json(name = "avatar_url") val avatarUrl: String,
    @param:Json(name = "gravatar_id") val gravatarId: String,
    val url: String,
    @param:Json(name = "html_url") val htmlUrl: String,
    @param:Json(name = "followers_url") val followersUrl: String,
    @param:Json(name = "following_url") val followingUrl: String,
    @param:Json(name = "gists_url") val gistsUrl: String,
    @param:Json(name = "starred_url") val starredUrl: String,
    @param:Json(name = "subscriptions_url") val subscriptionsUrl: String,
    @param:Json(name = "organizations_url") val organizationsUrl: String,
    @param:Json(name = "repos_url") val reposUrl: String,
    @param:Json(name = "events_url") val eventsUrl: String,
    @param:Json(name = "received_events_url") val receivedEventsUrl: String,
    val type: String,
    @param:Json(name = "user_view_type") val userViewType: String,
    @param:Json(name = "site_admin") val siteAdmin: Boolean
)
