package com.spoonart1.repositoryimpl.mapper

import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.model.RepositoryOwnerModel
import com.spoonart1.local.entity.RepositoryEntity
import com.spoonart1.data.remote.dto.RepositoryDto

fun RepositoryDto.toEntity(rankOrder: Int): RepositoryEntity =
    RepositoryEntity(
        id = id,
        name = name,
        description = description,
        ownerUsername = owner.login,
        ownerAvatarUrl = owner.avatarUrl,
        rankOrder = rankOrder
    )

fun RepositoryEntity.toModel(): RepositoryModel =
    RepositoryModel(
        id = id,
        name = name,
        description = description,
        owner = RepositoryOwnerModel(
            username = ownerUsername,
            avatarUrl = ownerAvatarUrl
        )
    )
