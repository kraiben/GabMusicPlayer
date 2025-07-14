package com.gab.gabsmusicplayer.ui.general.tracksList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gab.gabsmusicplayer.domain.models.PlaylistInfoModel
import com.gab.gabsmusicplayer.domain.models.TrackInfoModel
import com.gab.gabsmusicplayer.ui.playlistScreens.PlaylistScreenState
import kotlinx.coroutines.flow.Flow

@Composable
fun TrackOptionsMenu(
    onDismiss: () -> Unit,
    modifier: Modifier,
    playlistsState: PlaylistScreenState,
    state: TrackOptionsMenuState,
    creatingPlaylistResultFlow: Flow<Boolean>,
    playNext: (TrackInfoModel) -> Unit,
    createPlaylist: (TrackInfoModel, String) -> Unit,
    goToAddInPlaylistMenu: (TrackInfoModel) -> Unit,
    goToAddInNewPlaylistMenu: (TrackInfoModel) -> Unit,
    addToPlaylist: (TrackInfoModel, PlaylistInfoModel) -> Unit,
    removeFromPlaylist: (TrackInfoModel, PlaylistInfoModel) -> Unit,
) {
    AnimatedVisibility(state.isVisible) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .background(
                        MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(16)
                    )
                    .wrapContentHeight(align = Alignment.Bottom)
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                when (state) {
                    TrackOptionsMenuState.NotVisible -> {}
                    is TrackOptionsMenuState.AddToNewPlaylistMenu -> {
                        LaunchedEffect(Unit) {
                            creatingPlaylistResultFlow.collect {
                                if (it) onDismiss()
                            }
                        }
                        var text by remember { mutableStateOf("") }
                        OptionsMenuElement("Введите назвение") {}

                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .padding(8.dp)
                                .height(50.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .height(50.dp)
                                    .clickable { onDismiss() }, text = "Отмена"
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .clickable { createPlaylist(state.track, text) },
                                text = "Сохранить"
                            )
                        }
                    }

                    is TrackOptionsMenuState.AddToPlaylistMenu -> {
                        OptionsMenuElement("Выберите плейлист") { }
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        val playlists = if (playlistsState is PlaylistScreenState.Playlists) {
                            playlistsState.playlists
                        } else emptyList()
                        playlists.forEach { p ->
                            OptionsMenuElement(p.title) { addToPlaylist(state.track, p) }
                        }
                        OptionsMenuElement("Создать новый") { goToAddInNewPlaylistMenu(state.track) }
                    }

                    is TrackOptionsMenuState.AllTracksScreenMenu -> {
                        OptionsMenuElement(state.track.title) { }
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        OptionsMenuElement("Включить следующим") { playNext(state.track) }
                        OptionsMenuElement("Добавить в плейлист") { goToAddInPlaylistMenu(state.track) }
                    }

                    is TrackOptionsMenuState.PlaylistScreenMenu -> {
                        OptionsMenuElement(state.track.title) { }
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        OptionsMenuElement("Включить следующим") { playNext(state.track) }
                        OptionsMenuElement("Добавить в плейлист") { goToAddInPlaylistMenu(state.track) }
                        OptionsMenuElement("Убрать из плейлиста") {
                            removeFromPlaylist(
                                state.track,
                                state.playlist
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OptionsMenuElement(text: String, action: () -> Unit) {
    Text(
        textAlign = TextAlign.Start,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .clickable(onClick = action)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .padding(8.dp),
        color = Color.Black,
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.W400
    )
}