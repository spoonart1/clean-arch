package com.spoonart1.presentation.home

import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@Composable
fun GlideAvatar(
    avatarUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    var loadFailed by remember(avatarUrl) { mutableStateOf(false) }
    var retryKey by remember(avatarUrl) { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .then(
                if (loadFailed) Modifier.clickable {
                    loadFailed = false
                    retryKey++
                } else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        if (loadFailed) {
            Text(
                text = "Tap to retry",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(4.dp)
            )
        } else {
            AndroidView(
                modifier = Modifier.size(size),
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                },
                update = { imageView ->
                    retryKey
                    Glide.with(imageView)
                        .load(avatarUrl)
                        .listener(object : RequestListener<android.graphics.drawable.Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<android.graphics.drawable.Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                loadFailed = true
                                return false
                            }

                            override fun onResourceReady(
                                resource: android.graphics.drawable.Drawable,
                                model: Any,
                                target: Target<android.graphics.drawable.Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                loadFailed = false
                                return false
                            }
                        })
                        .into(imageView)
                }
            )
        }
    }
}
