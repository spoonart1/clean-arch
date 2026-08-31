package com.spoonart1.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LicenseDto(
    val key: String,
    val name: String,
    @param:Json(name = "spdx_id") val spdxId: String?,
    val url: String?,
    @param:Json(name = "node_id") val nodeId: String
)
