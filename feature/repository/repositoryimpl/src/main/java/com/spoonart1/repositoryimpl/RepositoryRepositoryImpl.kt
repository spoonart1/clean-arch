package com.spoonart1.repositoryimpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.spoonart1.data.remote.GitHubApi
import com.spoonart1.database.AppDatabase
import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.repository.RepositoryRepository
import com.spoonart1.local.dao.RepositoryDao
import com.spoonart1.repositoryimpl.mapper.toEntity
import com.spoonart1.repositoryimpl.mapper.toModel
import com.spoonart1.repositoryimpl.paging.RepositoryRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryRepositoryImpl @Inject constructor(
    private val api: GitHubApi,
    private val dao: RepositoryDao,
    private val database: AppDatabase
) : RepositoryRepository {

    override fun observeRepositories(): Flow<List<RepositoryModel>> =
        dao.observeRepositories().map { entities -> entities.map { it.toModel() } }

    override suspend fun refreshRepositories(query: String) {
        val response = api.searchRepositories(query = query, page = 1)
        dao.upsertAll(response.items.mapIndexed { index, dto -> dto.toEntity(rankOrder = index) })
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun pagedRepositories(query: String): Flow<PagingData<RepositoryModel>> =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = RepositoryRemoteMediator(query = query, api = api, database = database),
            pagingSourceFactory = { dao.pagingSource() }
        ).flow.map { pagingData -> pagingData.map { it.toModel() } }

    private companion object {
        const val PAGE_SIZE = 20
    }
}
