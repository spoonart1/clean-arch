package com.spoonart1.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spoonart1.local.entity.RepositoryRemoteKeysEntity

@Dao
interface RepositoryRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(keys: List<RepositoryRemoteKeysEntity>)

    @Query("SELECT * FROM repository_remote_keys WHERE repositoryId = :repositoryId")
    suspend fun remoteKeysByRepositoryId(repositoryId: Long): RepositoryRemoteKeysEntity?

    @Query("DELETE FROM repository_remote_keys")
    suspend fun clearAll()
}
