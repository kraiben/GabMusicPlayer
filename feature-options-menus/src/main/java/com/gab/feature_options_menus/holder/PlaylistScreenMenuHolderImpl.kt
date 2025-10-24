package com.gab.feature_options_menus.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.gab.feature_api_options_menus.PlaylistScreenMenuHolder
import com.gab.feature_options_menus.ui.PlaylistScreenMenuImpl
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class PlaylistScreenMenuHolderImpl @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): PlaylistScreenMenuHolder {
    @Composable
    override fun PlaylistScreenMenu(
        playlist: PlaylistInfoModel,
        track: TrackInfoModel,
        modifier: Modifier,
        onDismiss: () -> Unit
    ) {
        PlaylistScreenMenuImpl(
            modifier = modifier,
            track = track,
            onDismiss = onDismiss,
            viewModelFactory = viewModelFactory,
            playlist = playlist
        )
    }
}