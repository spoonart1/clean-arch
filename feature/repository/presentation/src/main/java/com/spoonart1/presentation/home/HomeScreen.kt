package com.spoonart1.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.model.RepositoryOwnerModel
import com.spoonart1.presentation.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val repositories = viewModel.repositories.collectAsLazyPagingItems()
    val context = LocalContext.current
    val hazeState = rememberHazeState()

    Scaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(R.drawable.wallpaper),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .hazeSource(hazeState),
                contentScale = ContentScale.Crop
            )
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent
            ) {
                RepositoryList(
                    repositories = repositories,
                    hazeState = hazeState,
                    onCardClick = { repository ->
                        Toast.makeText(
                            context,
                            "go to details ${repository.owner.username}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }
    }
}

@Composable
private fun RepositoryList(
    repositories: LazyPagingItems<RepositoryModel>,
    hazeState: HazeState,
    onCardClick: (RepositoryModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = repositories.itemCount,
            key = repositories.itemKey { it.id }
        ) { index ->
            val repository = repositories[index]
            if (repository != null) {
                RepositoryCard(repository = repository, onClick = onCardClick, hazeState = hazeState)
            }
        }

        val appendState = repositories.loadState.append
        if (appendState is LoadState.Loading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (appendState is LoadState.Error) {
            item {
                Text(
                    text = "Failed to load more: ${appendState.error.localizedMessage.orEmpty()}",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        val refreshState = repositories.loadState.refresh
        if (refreshState is LoadState.Loading && repositories.itemCount == 0) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryListPreview() {
    val sampleRepositories = listOf(
        RepositoryModel(
            id = 1,
            name = "compose-samples",
            description = "Official Jetpack Compose sample apps demonstrating best practices.",
            owner = RepositoryOwnerModel(
                username = "android",
                avatarUrl = "https://avatars.githubusercontent.com/u/878437"
            )
        ),
        RepositoryModel(
            id = 2,
            name = "retrofit",
            description = "A type-safe HTTP client for Android and the JVM.",
            owner = RepositoryOwnerModel(
                username = "square",
                avatarUrl = "https://avatars.githubusercontent.com/u/82592"
            )
        ),
        RepositoryModel(
            id = 3,
            name = "glide",
            description = null,
            owner = RepositoryOwnerModel(
                username = "bumptech",
                avatarUrl = "https://avatars.githubusercontent.com/u/423539"
            )
        )
    )

    val hazeState = rememberHazeState()

    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleRepositories, key = { it.id }) { repository ->
                    RepositoryCard(repository = repository, onClick = {}, hazeState = hazeState)
                }
            }
        }
    }
}
