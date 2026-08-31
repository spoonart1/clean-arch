package com.spoonart1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spoonart1.local.dao.RepositoryDao
import com.spoonart1.local.dao.RepositoryRemoteKeysDao
import com.spoonart1.local.entity.RepositoryEntity
import com.spoonart1.local.entity.RepositoryRemoteKeysEntity

@Database(
    entities = [RepositoryEntity::class, RepositoryRemoteKeysEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
    abstract fun repositoryRemoteKeysDao(): RepositoryRemoteKeysDao
}
