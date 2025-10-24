package com.gab.feature_player.ui

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
internal fun MiniPlayerImpl(
    modifier: Modifier= Modifier,
    viewModel: PlayerViewModel,
) {
    val currentTrack by viewModel.getCurrentTrack().collectAsState()
    val setPreviousTrack = remember { { viewModel.setPreviousTrack() } }
    val setNextTrack = remember { { viewModel.setNextTrack() } }
    val isPlaying by viewModel.getPlayingStatus().collectAsState()
    val playPauseStatusChange = remember { { viewModel.playPauseStatusChange() } }
    val isInitialized = viewModel.getIsInitializedFlow().collectAsState()
    LaunchedEffect(isInitialized.value) {
        Log.d("GAB_CHECK", "isInitialized = ${isInitialized.value}")
    }
    AnimatedVisibility(isInitialized.value) {
        ListItem(
            modifier = modifier
                .clip(
                    RoundedCornerShape(topEnd = 16f, topStart = 16f)
                ),
            leadingContent = {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f), contentDescription = null,
                    model = (ImageRequest.Builder(LocalContext.current)
                        .data(if (currentTrack.albumArtUri != Uri.EMPTY) {
                            currentTrack.albumArtUri
                        } else {
                            currentTrack.albumArtUriIsNullPatchId
                        })
                        .build())
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            headlineContent = {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = currentTrack.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            supportingContent = {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = currentTrack.artist,
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
                    IconButton(onClick = setPreviousTrack) {
                        Icon(

                            Icons.Default.SkipPrevious,
                            "Previous",
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f), tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    IconButton(onClick = playPauseStatusChange) {
                        Icon(

                            if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            if (isPlaying) "Pause" else "Play",
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f), tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    IconButton(onClick = setNextTrack) {
                        Icon(

                            Icons.Default.SkipNext,
                            "Next",
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f), tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        )
    }
}