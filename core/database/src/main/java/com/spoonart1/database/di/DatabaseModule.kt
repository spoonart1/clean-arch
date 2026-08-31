package com.spoonart1.database.di

import android.content.Context
import androidx.room.Room
import com.spoonart1.database.AppDatabase
import com.spoonart1.database.DatabaseConfig
import com.spoonart1.local.dao.RepositoryDao
import com.spoonart1.local.dao.RepositoryRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DatabaseConfig.DATABASE_NAME)
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    @Singleton
    fun provideRepositoryDao(database: AppDatabase): RepositoryDao = database.repositoryDao()

    @Provides
    @Singleton
    fun provideRepositoryRemoteKeysDao(database: AppDatabase): RepositoryRemoteKeysDao =
        database.repositoryRemoteKeysDao()
}
