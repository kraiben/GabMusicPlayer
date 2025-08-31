package com.gab.feature_all_tracks.holder

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.gab.music_entities_module.TrackInfoModel

public interface AllTTracksFeatureHolder {

    public fun NavGraphBuilder.allTracksScreen(
        modifier: Modifier = Modifier,
        onMenuButtonClick: () -> Unit,
        trackAddedToQueryMessage: (String) -> Unit,
        trackOptionsMenuContent: @Composable ((track: TrackInfoModel, onDismiss: () -> Unit) -> Unit)
    )

    public fun navigateToAllTracksScreen(navHostController: NavHostController)
    public fun getAllTracksScreenRoute(): String}