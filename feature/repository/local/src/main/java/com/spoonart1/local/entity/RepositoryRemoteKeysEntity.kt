package com.spoonart1.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repository_remote_keys")
data class RepositoryRemoteKeysEntity(
    @PrimaryKey val repositoryId: Long,
    val prevPage: Int?,
    val nextPage: Int?
)
