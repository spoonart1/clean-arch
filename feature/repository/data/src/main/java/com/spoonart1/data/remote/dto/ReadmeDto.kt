package com.spoonart1.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReadmeDto(
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val url: String,
    @param:Json(name = "html_url") val htmlUrl: String,
    @param:Json(name = "git_url") val gitUrl: String,
    @param:Json(name = "download_url") val downloadUrl: String?,
    val type: String,
    val content: String,
    val encoding: String,
    @param:Json(name = "_links") val links: ReadmeLinksDto
)

@JsonClass(generateAdapter = true)
data class ReadmeLinksDto(
    val self: String,
    val git: String,
    val html: String
)
