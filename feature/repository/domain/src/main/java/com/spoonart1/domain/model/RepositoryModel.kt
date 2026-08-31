package com.spoonart1.domain.model

data class RepositoryModel(
    val id: Long,
    val name: String,
    val description: String?,
    val owner: RepositoryOwnerModel
)