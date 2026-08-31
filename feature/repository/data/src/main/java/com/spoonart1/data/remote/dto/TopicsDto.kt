package com.spoonart1.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopicsDto(
    val names: List<String>
)
