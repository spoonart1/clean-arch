package com.spoonart1.domain.repository

import com.spoonart1.domain.model.RepositoryModel
import kotlinx.coroutines.flow.Flow

interface RepositoryRepository {
    fun observeRepositories(): Flow<List<RepositoryModel>>
    suspend fun refreshRepositories(query: String)
}
