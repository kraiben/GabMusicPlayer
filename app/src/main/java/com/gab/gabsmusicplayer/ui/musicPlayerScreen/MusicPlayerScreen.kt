package com.gab.gabsmusicplayer.ui.musicPlayerScreen

import android.net.Uri
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.gabsmusicplayer.R
import java.util.Locale

@Composable
fun MusicPlayerScreen(
    isTrackPlaying: Boolean,
    currentPosition: Long,
    title: String,
    artist: String,
    duration: Long,
    imageUri: Uri,
    isShuffled: Boolean = false,
    isOneRepeating: Boolean = false,
    onPreviousButtonClick: () -> Unit,
    onPlayPauseButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onSliderChange: (Long) -> Unit,
    onShuffleButtonClick: () -> Unit = {},
    onRepeatModeButtonClick: () -> Unit = {},

    ) {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f), contentDescription = null,
            model = (ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .error(R.drawable.miku_headphones)
                .build())
        )
        Text(
            text = title,
            maxLines = 1,
            fontSize = 28.sp,
            fontWeight = FontWeight.W400,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 12.dp)
        )
        Spacer(
            modifier = Modifier.size(8.dp)
        )
        Text(
            text = artist,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.W100,
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 12.dp),
            overflow = TextOverflow.Ellipsis
        )

        MediaPlayerSlider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            duration = duration,
            onValueChanged = { newPosition ->
                onSliderChange(newPosition)
            },
            currentPosition = currentPosition
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onShuffleButtonClick() }) {
                Icon(
                    contentDescription = null, imageVector =
                    if (isShuffled) Icons.Default.ShuffleOn else Icons.Default.Shuffle
                )
            }
            IconButton(onClick = { onPreviousButtonClick() }) {
                Icon(Icons.Default.SkipPrevious, "Previous", modifier = Modifier.size(80.dp))
            }

            IconButton(onClick = {
                onPlayPauseButtonClick()
            }) {
                Icon(
                    if (isTrackPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    if (isTrackPlaying) "Pause" else "Play", modifier = Modifier.size(80.dp)
                )
            }
            IconButton(onClick = { onNextButtonClick() }) {
                Icon(Icons.Default.SkipNext, "Next", modifier = Modifier.size(80.dp))
            }
            IconButton(onClick = { onRepeatModeButtonClick() }) {
                Icon(
                    contentDescription = null,
                    imageVector = if (isOneRepeating) Icons.Default.RepeatOne else Icons.Default.Repeat
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