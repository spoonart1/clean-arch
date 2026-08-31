package com.spoonart1.domain.usecase

import androidx.paging.PagingData
import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.repository.RepositoryRepository
import kotlinx.coroutines.flow.Flow

class GetPagedRepositoriesUseCase(
    private val repository: RepositoryRepository
) {
    operator fun invoke(query: String): Flow<PagingData<RepositoryModel>> =
        repository.pagedRepositories(query)
}
