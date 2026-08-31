package com.spoonart1.repositoryimpl.di

import com.spoonart1.domain.repository.RepositoryRepository
import com.spoonart1.repositoryimpl.RepositoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindsModule {

    @Binds
    @Singleton
    abstract fun bindRepositoryRepository(impl: RepositoryRepositoryImpl): RepositoryRepository
}
