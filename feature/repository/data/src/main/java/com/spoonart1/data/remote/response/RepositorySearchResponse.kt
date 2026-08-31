package com.spoonart1.data.remote.response

import com.spoonart1.data.remote.dto.RepositoryDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositorySearchResponse(
    @param:Json(name = "total_count") val totalCount: Int,
    @param:Json(name = "incomplete_results") val incompleteResults: Boolean,
    val items: List<RepositoryDto>
)
