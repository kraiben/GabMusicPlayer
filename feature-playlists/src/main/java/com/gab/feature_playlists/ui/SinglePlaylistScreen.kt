package com.gab.feature_playlists.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.feature_playlists.ui.components.LoadingCircle
import com.gab.feature_playlists.ui.components.PlaylistsScreenState
import com.gab.feature_playlists.ui.components.TrackListElement
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel

@Composable
internal fun SinglePlaylistScreen(
    modifier: Modifier = Modifier,
    playlistId: Long,
    navigateToPlaylistEditScreen: (PlaylistInfoModel) -> Unit,
    trackOptionsMenuContent: @Composable ((TrackInfoModel, PlaylistInfoModel, () -> Unit) -> Unit),
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel = viewModel<PlaylistsFeatureViewModel>(factory = viewModelFactory)

    val playlistsState = viewModel.getPlaylists().collectAsState()

    when (val _playlists = playlistsState.value) {
        PlaylistsScreenState.Initial -> {
            LoadingCircle()
        }

        PlaylistsScreenState.Loading -> {
            LoadingCircle()
        }

        is PlaylistsScreenState.Playlists -> {
            val playlist = _playlists.playlists.find { it.id == playlistId } ?: PlaylistInfoModel.EMPTY
            Box() {
                var currentOpenedMenuTrack by remember { mutableStateOf<TrackInfoModel?>(null) }
                val context = LocalContext.current

                var coverImageKey by remember { mutableLongStateOf(System.currentTimeMillis()) }
                val imagePickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    uri?.let {
                        viewModel.setPlaylistPicture(playlist, it)
                        coverImageKey = System.currentTimeMillis()
                    } ?: Toast.makeText(context, "Не выбрана картинка", Toast.LENGTH_SHORT).show()
                }
                val trackListElementModifier = remember {
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                }
                val onTrackClickListener = remember { {tracks: List<TrackInfoModel>, index: Int ->
                    viewModel.setTrackQueue(tracks, index)
                } }
                val menuButtonClickListener = remember { {track: TrackInfoModel ->
                    currentOpenedMenuTrack = track
                } }
                LazyColumn(
                    modifier = modifier
                ) {
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
                                            viewModel.setRandomTracksQueue(playlist.tracks)
                                        },
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Icon(
                                    imageVector = Icons.Default.EditNote,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clickable { navigateToPlaylistEditScreen(playlist) },
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
                            modifier = trackListElementModifier
                                .combinedClickable(
                                    onClick = { onTrackClickListener(playlist.tracks, index) }
                                ),
                            menuButtonClickListener = {
                                menuButtonClickListener(track)
                            }
                        )
                    }
                }
                currentOpenedMenuTrack?.let {
                    trackOptionsMenuContent(it, playlist) {
                        currentOpenedMenuTrack = null
                    }
                }
            }
        }
    }
}

