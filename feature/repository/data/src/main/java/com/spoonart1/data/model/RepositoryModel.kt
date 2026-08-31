package com.spoonart1.data.model

data class RepositoryModel(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: RepositoryOwnerModel
)