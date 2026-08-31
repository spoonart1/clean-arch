package com.spoonart1.repositoryimpl.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.spoonart1.data.remote.GitHubApi
import com.spoonart1.database.AppDatabase
import com.spoonart1.local.entity.RepositoryEntity
import com.spoonart1.local.entity.RepositoryRemoteKeysEntity
import com.spoonart1.repositoryimpl.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class RepositoryRemoteMediator(
    private val query: String,
    private val api: GitHubApi,
    private val database: AppDatabase
) : RemoteMediator<Int, RepositoryEntity>() {

    private val dao = database.repositoryDao()
    private val remoteKeysDao = database.repositoryRemoteKeysDao()

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> STARTING_PAGE
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                val nextPage = remoteKeysDao.remoteKeysByRepositoryId(lastItem.id)?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextPage
            }
        }

        return try {
            val response = api.searchRepositories(query = query, page = page, perPage = state.config.pageSize)
            val endOfPaginationReached = response.items.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearAll()
                    dao.clearAll()
                }

                val rankOffset = (page - STARTING_PAGE) * state.config.pageSize
                val entities = response.items.mapIndexed { index, dto ->
                    dto.toEntity(rankOrder = rankOffset + index)
                }
                val nextPage = if (endOfPaginationReached) null else page + 1
                val keys = entities.map { entity ->
                    RepositoryRemoteKeysEntity(
                        repositoryId = entity.id,
                        prevPage = if (page == STARTING_PAGE) null else page - 1,
                        nextPage = nextPage
                    )
                }

                remoteKeysDao.upsertAll(keys)
                dao.upsertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private companion object {
        const val STARTING_PAGE = 1
    }
}
