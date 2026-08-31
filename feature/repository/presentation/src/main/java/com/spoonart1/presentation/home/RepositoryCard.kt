package com.spoonart1.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spoonart1.domain.model.RepositoryModel
import com.spoonart1.domain.model.RepositoryOwnerModel
import com.spoonart1.presentation.R
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun RepositoryCard(
    repository: RepositoryModel,
    onClick: (RepositoryModel) -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = CardDefaults.shape)
            .clip(CardDefaults.shape)
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.thin(Color.White)
            )
            .clickable { onClick(repository) }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header: icon --- title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = colorResource(R.color.card_bg_color)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlideAvatar(
                    avatarUrl = repository.owner.avatarUrl,
                    size = 32.dp
                )
                Text(
                    text = repository.name.uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(start = 12.dp),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // Body: description
                val description = repository.description
                if (!description.isNullOrBlank()) {
                    Text(
                        text = description,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(vertical = 12.dp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        color = colorResource(R.color.card_abc_desc_color)
                    )
                }

                // Footer: subtitle
                Text(
                    text = repository.owner.username,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryCardPreview() {
    val hazeState = rememberHazeState()

    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                RepositoryCard(
                    repository = RepositoryModel(
                        id = 1,
                        name = "compose-samples",
                        description = "Official Jetpack Compose sample apps demonstrating best practices.",
                        owner = RepositoryOwnerModel(
                            username = "android",
                            avatarUrl = "https://avatars.githubusercontent.com/u/878437"
                        )
                    ),
                    onClick = {},
                    hazeState = hazeState
                )
                RepositoryCard(
                    repository = RepositoryModel(
                        id = 2,
                        name = "glide",
                        description = null,
                        owner = RepositoryOwnerModel(
                            username = "bumptech",
                            avatarUrl = "https://avatars.githubusercontent.com/u/423539"
                        )
                    ),
                    onClick = {},
                    hazeState = hazeState
                )
            }
        }
    }
}
