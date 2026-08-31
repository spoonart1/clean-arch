package com.spoonart1.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spoonart1.local.dao.RepositoryDao
import com.spoonart1.local.entity.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repositoryDao(): RepositoryDao
}
