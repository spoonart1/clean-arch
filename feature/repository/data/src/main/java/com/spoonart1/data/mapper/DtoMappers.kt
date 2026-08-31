package com.spoonart1.data.mapper

import com.spoonart1.data.model.LicenseModel
import com.spoonart1.data.model.ReadmeModel
import com.spoonart1.data.model.RepositoryModel
import com.spoonart1.data.model.RepositoryOwnerModel
import com.spoonart1.data.model.TopicsModel
import com.spoonart1.data.remote.dto.LicenseDto
import com.spoonart1.data.remote.dto.OwnerDto
import com.spoonart1.data.remote.dto.ReadmeDto
import com.spoonart1.data.remote.dto.RepositoryDto
import com.spoonart1.data.remote.dto.TopicsDto

fun RepositoryDto.toModel(): RepositoryModel =
    RepositoryModel(
        id = id,
        name = name,
        description = description,
        owner = owner.toModel()
    )

fun OwnerDto.toModel(): RepositoryOwnerModel =
    RepositoryOwnerModel(
        username = login,
        avatarUrl = avatarUrl
    )

fun LicenseDto.toModel(): LicenseModel =
    LicenseModel(
        key = key,
        name = name,
        spdxId = spdxId,
        url = url
    )

fun TopicsDto.toModel(): TopicsModel =
    TopicsModel(
        names = names
    )

fun ReadmeDto.toModel(): ReadmeModel =
    ReadmeModel(
        name = name,
        path = path,
        htmlUrl = htmlUrl,
        downloadUrl = downloadUrl,
        content = content,
        encoding = encoding
    )
