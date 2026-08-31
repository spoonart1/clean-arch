package com.spoonart1.cleanarch.di

import com.spoonart1.domain.repository.RepositoryRepository
import com.spoonart1.domain.usecase.GetPagedRepositoriesUseCase
import com.spoonart1.domain.usecase.ObserveRepositoriesUseCase
import com.spoonart1.domain.usecase.RefreshRepositoriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideObserveRepositoriesUseCase(repository: RepositoryRepository): ObserveRepositoriesUseCase =
        ObserveRepositoriesUseCase(repository)

    @Provides
    fun provideRefreshRepositoriesUseCase(repository: RepositoryRepository): RefreshRepositoriesUseCase =
        RefreshRepositoriesUseCase(repository)

    @Provides
    fun provideGetPagedRepositoriesUseCase(repository: RepositoryRepository): GetPagedRepositoriesUseCase =
        GetPagedRepositoriesUseCase(repository)
}
