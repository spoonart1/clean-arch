package com.spoonart1.data.model

data class ReadmeModel(
    val name: String,
    val path: String,
    val htmlUrl: String,
    val downloadUrl: String?,
    val content: String,
    val encoding: String
)
