package com.gab.gabsmusicplayer.ui.musicPlayerScreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.gabsmusicplayer.R
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun MusicPlayerScreen(
    mediaController: State<MediaController?>,

    ) {
    var currentMediaItem by remember { mutableStateOf(mediaController.value?.currentMediaItem) }
    var isTrackPlaying by remember { mutableStateOf(mediaController.value?.isPlaying ?: false) }
    var currentPosition by remember { mutableStateOf(mediaController.value?.currentPosition ?: 0L) }
    var title by remember { mutableStateOf(currentMediaItem?.mediaMetadata?.title.toString()) }
    var duration by remember { mutableStateOf(currentMediaItem?.mediaMetadata?.durationMs ?: 1L) }
    var imageUri by remember {
        mutableStateOf(
            currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
        )
    }
    LaunchedEffect(mediaController.value) {
        val controller = mediaController.value ?: return@LaunchedEffect

        controller.addListener(
            object : Player.Listener {

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                    isTrackPlaying = isPlaying
                }

                override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
                    super.onPlaylistMetadataChanged(mediaMetadata)
                    currentMediaItem = mediaController.value?.currentMediaItem
                    title = currentMediaItem?.mediaMetadata?.title.toString()
                    duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
                    imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
                }
                override fun onIsLoadingChanged(isLoading: Boolean) {
                    super.onIsLoadingChanged(isLoading)
                    currentMediaItem = mediaController.value?.currentMediaItem
                    title = currentMediaItem?.mediaMetadata?.title.toString()
                    duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
                    imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                    currentMediaItem = mediaController.value?.currentMediaItem
                    title = currentMediaItem?.mediaMetadata?.title.toString()
                    duration = currentMediaItem?.mediaMetadata?.durationMs ?: 0L
                    imageUri = currentMediaItem?.mediaMetadata?.artworkUri ?: Uri.EMPTY
                }
            }
        )
    }
    LaunchedEffect(mediaController) {
        while (true) {
            delay(250)
            currentPosition = mediaController.value?.currentPosition ?: 0L
        }
    }
    GAB_CHECK(
        """
        title: $title
        currentPosition: $currentPosition
        imageUri: $imageUri
        isPlaying: $isTrackPlaying
        duration: $duration
    """.trimIndent()
    )
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), contentDescription = null,
            model = (ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .error(listOf(R.drawable.megumindk, R.drawable.miku_headphones).random())
                .build())
        )
        Text(text = title)

        MediaPlayerSlider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            duration = duration,
            onValueChanged = { newPosition ->
                mediaController.value?.seekTo(newPosition)
            },
            currentPosition = currentPosition
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { mediaController.value?.seekToPrevious() }) {
                Icon(Icons.Default.SkipPrevious, "Previous", modifier = Modifier.size(80.dp))
            }

            IconButton(onClick = {
                if (isTrackPlaying) mediaController.value?.pause()
                else mediaController.value?.play()
            }) {
                Icon(
                    if (isTrackPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    if (isTrackPlaying) "Pause" else "Play", modifier = Modifier.size(80.dp)
                )
            }

            IconButton(onClick = { mediaController.value?.seekToNext() }) {
                Icon(Icons.Default.SkipNext, "Next", modifier = Modifier.size(80.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPlayerSlider(
    currentPosition: Long,
    duration: Long,
    onValueChanged: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    fun formatTime(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        return String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
    }
    Column(
        modifier = modifier
    ) {
        val thumbSize = 8.dp
        val trackHeight = 4.dp
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = { newValue ->
                onValueChanged(newValue.toLong())
            },
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.size(thumbSize).offset(y = (thumbSize - trackHeight)),
                    colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.onPrimary)
                )
            },
            valueRange = 0f..duration.toFloat(),
            track = {
                Box(
                    modifier = Modifier
                        .height(trackHeight)
                        .fillMaxWidth()
                        .background(Color.Gray)
                ) {
                    Box(
                        modifier = Modifier
                            .height(trackHeight)
                            .fillMaxWidth(currentPosition / duration.toFloat())
                            .background(MaterialTheme.colorScheme.onPrimary)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
        )
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentPosition),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                fontSize = 12.sp,
                text = formatTime(duration),
                textAlign = TextAlign.Center
            )
        }
    }

}