package com.spoonart1.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.usecase.GetPagedRepositoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val DEFAULT_QUERY = "stars:>1"

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPagedRepositoriesUseCase: GetPagedRepositoriesUseCase
) : ViewModel() {

    val repositories: Flow<PagingData<RepositoryModel>> =
        getPagedRepositoriesUseCase(DEFAULT_QUERY).cachedIn(viewModelScope)
}
