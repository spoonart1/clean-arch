package com.spoonart1.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String?,
    val ownerUsername: String,
    val ownerAvatarUrl: String
)
