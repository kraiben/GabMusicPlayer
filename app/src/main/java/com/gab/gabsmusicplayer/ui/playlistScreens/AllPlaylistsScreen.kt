package com.gab.gabsmusicplayer.ui.playlistScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gab.gabsmusicplayer.ui.general.LoadingCircle
import com.gab.gabsmusicplayer.ui.navigation.NavigationState

@Composable
fun AllPlaylistsScreen(
    paddingValues: PaddingValues,
    navigationState: NavigationState,
    playlistsState: PlaylistScreenState,
    onCreateNewPlaylist: () -> Unit,
) {

    when (playlistsState) {
        PlaylistScreenState.Initial -> {
            LoadingCircle()
        }

        PlaylistScreenState.Loading -> {
            LoadingCircle()
        }

        is PlaylistScreenState.Playlists -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(4.dp)
            ) {
                items(items = playlistsState.playlists, key = { it.id }) { playlist ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { navigationState.navigateToPlaylist(playlist.id) }
                            .padding(horizontal = 4.dp)
                            .background(color = MaterialTheme.colorScheme.background)
                            .aspectRatio(0.75F)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12))
                                .aspectRatio(1f), contentDescription = null,
                            model = (ImageRequest.Builder(LocalContext.current)
                                .data(playlist.coverUri)
                                .error(playlist.fallbackCover)
                                .memoryCacheKey("${playlist.coverUri}-${playlist.fallbackCover}")
                                .build())
                        )
                        Text(
                            text = playlist.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable { onCreateNewPlaylist() }
                            .padding(horizontal = 4.dp)
                            .background(color = MaterialTheme.colorScheme.background)
                            .aspectRatio(0.75F)
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier.fillMaxSize(0.5f),
                                imageVector = Icons.Default.AddCircle,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null)
                        }



                        Text(
                            text = "Создать новый",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W200,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }


}