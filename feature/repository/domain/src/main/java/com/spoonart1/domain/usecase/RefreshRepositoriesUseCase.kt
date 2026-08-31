package com.spoonart1.domain.usecase

import com.spoonart1.domain.repository.RepositoryRepository

class RefreshRepositoriesUseCase(
    private val repository: RepositoryRepository
) {
    suspend operator fun invoke(query: String) = repository.refreshRepositories(query)
}
