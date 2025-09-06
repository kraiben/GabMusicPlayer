package com.gab.feature_options_menus.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.gab.feature_options_menus.ui.base.OptionsMenuDivider
import com.gab.feature_options_menus.ui.base.OptionsMenuElement
import com.gab.music_entities_module.TrackInfoModel

@Composable
internal fun AddToPlaylistMenu(
    viewModel: TracksOptionsMenuViewModel,
    track: TrackInfoModel,
    onDismiss: () -> Unit,
    goToAddInNewPlaylistMenu: () -> Unit
) {

    OptionsMenuElement("Выберите плейлист") { }
    OptionsMenuDivider()
    val playlists = viewModel.getPlaylists().collectAsState()
    playlists.value.forEach { p ->
        OptionsMenuElement(p.title) {
            viewModel.addToPlaylist(p, track)
            onDismiss()
        }
    }
    OptionsMenuElement("Создать новый", action = goToAddInNewPlaylistMenu)
}