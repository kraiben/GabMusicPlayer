package com.gab.gabsmusicplayer.ui.musicPlayerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.ShuffleOn
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.gabsmusicplayer.ui.screens.main.MusicViewModel
import java.util.Locale

@Composable
fun MusicPlayerScreen(
    viewModel: MusicViewModel,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16))
                .fillMaxWidth()
                .aspectRatio(1f),
            contentDescription = null,
            model = (ImageRequest.Builder(LocalContext.current)
                .data(viewModel.imageUri)
                .error(viewModel.artworkData)
                .memoryCacheKey("${viewModel.imageUri}-${viewModel.artworkData}")
                .build())
        )
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = viewModel.title,
            maxLines = 1,
            fontSize = 28.sp,
            fontWeight = FontWeight.W400,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = viewModel.artist,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.W100,
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 24.dp),
            overflow = TextOverflow.Ellipsis
        )

        MediaPlayerSlider(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            duration = viewModel.duration,
            onValueChanged = { newPosition ->
                viewModel.onSliderChange(newPosition)
            },
            currentPosition = viewModel.currentPosition
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { viewModel.shuffleStateChange() }) {
                Icon(
                    contentDescription = null,
                    imageVector =
                    if (viewModel.isShuffleModeSet) Icons.Default.ShuffleOn else Icons.Default.Shuffle,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { viewModel.previousTrack() }) {
                Icon(
                    Icons.Default.SkipPrevious,
                    "Previous",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(onClick = {
                viewModel.playPauseChange()
            }) {
                Icon(
                    if (viewModel.isTrackPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    if (viewModel.isTrackPlaying) "Pause" else "Play",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { viewModel.nextTrack() }) {
                Icon(
                    Icons.Default.SkipNext,
                    "Next",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = { viewModel.isRepeatingOneStateChange() }) {
                Icon(
                    contentDescription = null,
                    imageVector = if (viewModel.isRepeatingOne) Icons.Default.RepeatOne else Icons.Default.Repeat,
                    tint = MaterialTheme.colorScheme.onBackground
                )
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
                    modifier = Modifier
                        .size(thumbSize)
                        .offset(y = (thumbSize - trackHeight)),
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
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                fontSize = 12.sp,
                text = formatTime(duration),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}