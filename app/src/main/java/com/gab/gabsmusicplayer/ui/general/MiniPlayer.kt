package com.gab.gabsmusicplayer.ui.general

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MiniPlayer(
    modifier: Modifier,
    onClick: () -> Unit,
    isTrackPlaying: Boolean,
    title: String,
    artist: String,
    albumArtUri: Uri,
    artworkData: Int,
    onPreviousButtonClick: () -> Unit,
    onPlayPauseButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
) {
    ListItem(
        modifier = modifier
            .clip(
                RoundedCornerShape(topEnd = 16f, topStart = 16f)
            )
            .clickable { onClick() },
        leadingContent = {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f), contentDescription = null,
                model = (ImageRequest.Builder(LocalContext.current)
                    .data(albumArtUri)
                    .error(artworkData)
                    .memoryCacheKey("$albumArtUri-$artworkData")
                    .build())
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        headlineContent = {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleSmall
            )
        },
        supportingContent = {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )
        },
        trailingContent = {
            Row(
                modifier = Modifier
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onPreviousButtonClick() }) {
                    Icon(

                        Icons.Default.SkipPrevious,
                        "Previous",
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                        , tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(onClick = {
                    onPlayPauseButtonClick()
                }) {
                    Icon(

                        if (isTrackPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        if (isTrackPlaying) "Pause" else "Play",
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                        , tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(onClick = { onNextButtonClick() }) {
                    Icon(

                        Icons.Default.SkipNext,
                        "Next",
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                        , tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    )
}