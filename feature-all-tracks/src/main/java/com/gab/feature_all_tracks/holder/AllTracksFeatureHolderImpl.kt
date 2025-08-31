package com.gab.feature_all_tracks.holder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gab.feature_all_tracks.GAB_CHECK
import com.gab.feature_all_tracks.di.AllTracksFeatureComponent
import com.gab.feature_all_tracks.ui.AllTracksScreenImpl
import com.gab.feature_all_tracks.ui.LoadingCircle
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject


public class AllTracksFeatureHolderImpl
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
) : AllTTracksFeatureHolder {

    override fun NavGraphBuilder.allTracksScreen(
        modifier: Modifier,
        onMenuButtonClick: () -> Unit,
        trackAddedToQueryMessage: (String) -> Unit,
        trackOptionsMenuContent: @Composable ((track: TrackInfoModel, onDismiss: () -> Unit) -> Unit),
    ) {
        composable(
            route = ROUTE_ALL_TRACKS_SCREEN
        ) {
            GAB_CHECK("adkbvg")
            AllTracksScreenImpl(
                modifier,
                onMenuButtonClick,
                trackOptionsMenuContent,
                trackAddedToQueryMessage,
                viewModelFactory
            )
        }
    }

    override fun navigateToAllTracksScreen(navHostController: NavHostController) {
        navHostController.navigate(ROUTE_ALL_TRACKS_SCREEN) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    override fun getAllTracksScreenRoute(): String = ROUTE_ALL_TRACKS_SCREEN


    private companion object {
        private const val ROUTE_ALL_TRACKS_SCREEN: String = "ROUTE_ALL_TRACKS_SCREEN"
    }

}

