package com.spoonart1.repositoryimpl

import com.spoonart1.data.remote.GitHubApi
import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.repository.RepositoryRepository
import com.spoonart1.local.dao.RepositoryDao
import com.spoonart1.repositoryimpl.mapper.toEntity
import com.spoonart1.repositoryimpl.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryRepositoryImpl @Inject constructor(
    private val api: GitHubApi,
    private val dao: RepositoryDao
) : RepositoryRepository {

    override fun observeRepositories(): Flow<List<RepositoryModel>> =
        dao.observeRepositories().map { entities -> entities.map { it.toModel() } }

    override suspend fun refreshRepositories(query: String) {
        val response = api.searchRepositories(query = query, page = 1)
        dao.upsertAll(response.items.map { it.toEntity() })
    }
}
