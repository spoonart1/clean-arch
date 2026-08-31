package com.spoonart1.domain.usecase

import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.repository.RepositoryRepository
import kotlinx.coroutines.flow.Flow

class ObserveRepositoriesUseCase(
    private val repository: RepositoryRepository
) {
    operator fun invoke(): Flow<List<RepositoryModel>> = repository.observeRepositories()
}
