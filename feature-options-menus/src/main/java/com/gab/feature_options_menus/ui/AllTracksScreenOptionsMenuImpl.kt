package com.gab.feature_options_menus.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gab.feature_options_menus.ui.base.MenuBase
import com.gab.feature_options_menus.ui.base.OptionsMenuDivider
import com.gab.feature_options_menus.ui.base.OptionsMenuElement
import com.gab.music_entities_module.TrackInfoModel

@Composable
internal fun AllTracksScreenOptionsMenuImpl(
    track: TrackInfoModel,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    viewModelFactory: ViewModelProvider.Factory,
) {
    val viewModel: TracksOptionsMenuViewModel = viewModel(factory = viewModelFactory)
    MenuBase(onDismiss = onDismiss) {
        var tracksOptionsMenuScreenState: TracksOptionsMenuScreenState? by
        remember { mutableStateOf(null) }
        LaunchedEffect(Unit) {
            tracksOptionsMenuScreenState = TracksOptionsMenuScreenState.AllTracksScreenMenu
        }

        when (tracksOptionsMenuScreenState) {
            TracksOptionsMenuScreenState.AllTracksScreenMenu -> {
                OptionsMenuElement(track.title) { }
                OptionsMenuDivider()
                OptionsMenuElement("Включить следующим") {
                    viewModel.setNextTrack(track)
                    onDismiss()
                }
                OptionsMenuElement("Добавить в плейлист") {
                    tracksOptionsMenuScreenState =
                        TracksOptionsMenuScreenState.AddToPlaylistMenu
                }
            }

            TracksOptionsMenuScreenState.AddToPlaylistMenu -> {
                AddToPlaylistMenu(viewModel, track, onDismiss) {
                    tracksOptionsMenuScreenState =
                        TracksOptionsMenuScreenState.AddToNewPlaylistMenu
                }
            }

            TracksOptionsMenuScreenState.AddToNewPlaylistMenu -> {
                AddToNewPlaylistMenu(track, viewModel, onDismiss)
            }

            else -> {}
        }
    }
}