package com.gab.gabsmusicplayer.ui.playlistScreens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.gabsmusicplayer.ui.general.LoadingCircle
import com.gab.gabsmusicplayer.ui.general.tracksList.TrackListElement
import com.gab.core_music_loading.models.PlaylistInfoModel
import com.gab.core_music_loading.models.TrackInfoModel

@Composable
fun SinglePlaylistScreen(
    playlistId: Long,
    onTrackClickListener: (List<TrackInfoModel>, Int) -> Unit,
    onMainPlayButtonClickListener: (List<TrackInfoModel>) -> Unit,
    setPlaylistPicture: (PlaylistInfoModel, uri: Uri) -> Unit,
    playlistsState: PlaylistScreenState,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    playlistEdit: (PlaylistInfoModel) -> Unit,
    trackOptionsMenuClickListener: (TrackInfoModel, PlaylistInfoModel) -> Unit
) {

    when (playlistsState) {
        PlaylistScreenState.Initial -> {
            LoadingCircle()
        }

        PlaylistScreenState.Loading -> {
            LoadingCircle()
        }

        is PlaylistScreenState.Playlists -> {
            val playlist =
                playlistsState.playlists.find { it.id == playlistId } ?: PlaylistInfoModel.EMPTY
            val context = LocalContext.current

            var coverImageKey by remember { mutableLongStateOf(System.currentTimeMillis()) }
            val imagePickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri: Uri? ->
                uri?.let {
                    setPlaylistPicture(playlist, it)
                    coverImageKey = System.currentTimeMillis()
                } ?: Toast.makeText(context, "Не выбрана картинка", Toast.LENGTH_SHORT).show()
            }
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            )
            {
                item {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(
                                brush = Brush.verticalGradient(
                                    colorStops = arrayOf(
                                        0.0f to MaterialTheme.colorScheme.primary,
                                        0.7f to MaterialTheme.colorScheme.background
                                    ),
                                    startY = 0f,
                                    endY = Float.POSITIVE_INFINITY,
                                )
                            )
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        AsyncImage(
                            model = (ImageRequest.Builder(LocalContext.current)
                                .data(playlist.coverUri)
                                .error(playlist.fallbackCover)
                                .memoryCacheKey("${playlist.coverUri.path}-${playlist.fallbackCover}-${coverImageKey}")
                                .build()),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12)),
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            color = MaterialTheme.colorScheme.onBackground,
                            text = playlist.title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W400,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Image,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        imagePickerLauncher.launch("image/*")
                                    },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.Default.PlayCircle,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable {
                                        onMainPlayButtonClickListener(playlist.tracks)
                                    },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable { playlistEdit(playlist) },
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                itemsIndexed(
                    items = playlist.tracks,
                    key = { _: Int, track -> track.id })
                { index: Int, track ->
                    TrackListElement(
                        track = track,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .combinedClickable(
                                onClick = { onTrackClickListener(playlist.tracks, index) }
                            ),
                        menuButtonClickListener = {trackOptionsMenuClickListener(track, playlist)}
                    )
                }
            }
        }
    }
}

