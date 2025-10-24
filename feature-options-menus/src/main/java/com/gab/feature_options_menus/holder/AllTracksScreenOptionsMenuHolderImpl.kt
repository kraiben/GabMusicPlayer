package com.gab.feature_options_menus.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.gab.feature_api_options_menus.AllTracksScreenOptionsMenuHolder
import com.gab.feature_options_menus.ui.AllTracksScreenOptionsMenuImpl
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

public class AllTracksScreenOptionsMenuHolderImpl @Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory
): AllTracksScreenOptionsMenuHolder {
    @Composable
    override fun AllTracksScreenOptionsMenu(
        track: TrackInfoModel,
        modifier: Modifier,
        onDismiss: () -> Unit
    ) {
        AllTracksScreenOptionsMenuImpl(
            modifier = modifier,
            track = track,
            onDismiss = onDismiss,
            viewModelFactory = viewModelFactory,
        )
    }

}
