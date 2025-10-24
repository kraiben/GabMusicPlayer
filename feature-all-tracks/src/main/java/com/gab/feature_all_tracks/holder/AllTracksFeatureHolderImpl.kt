package com.gab.feature_all_tracks.holder

import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gab.feature_all_tracks.GAB_CHECK
import com.gab.feature_all_tracks.ui.AllTracksScreenImpl
import com.gab.feature_api_all_tracks.AllTTracksFeatureHolder
import com.gab.feature_api_options_menus.AllTracksScreenOptionsMenuHolder
import javax.inject.Inject

public class AllTracksFeatureHolderImpl
@Inject constructor(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val trackOptionsMenuHolder: AllTracksScreenOptionsMenuHolder,
) : AllTTracksFeatureHolder {

    override fun NavGraphBuilder.allTracksScreen(
        modifier: Modifier,
        onMenuButtonClick: () -> Unit,
        trackAddedToQueryMessage: (String) -> Unit,
    ) {
        composable(
            route = ROUTE_ALL_TRACKS_SCREEN
        ) {
            GAB_CHECK("adkbvg")
            AllTracksScreenImpl(
                modifier,
                trackOptionsMenuHolder,
                onMenuButtonClick,
                trackAddedToQueryMessage,
                viewModelFactory
            )
        }
    }

//    override fun navigateToAllTracksScreen(navHostController: NavHostController) {
//        navHostController.navigate(ROUTE_ALL_TRACKS_SCREEN) {
//            popUpTo(navHostController.graph.findStartDestination().id) {
//                saveState = true
//            }
//            restoreState = true
//            launchSingleTop = true
//        }
//    }

    override fun getAllTracksScreenRoute(): String = ROUTE_ALL_TRACKS_SCREEN


    private companion object {
        private const val ROUTE_ALL_TRACKS_SCREEN: String = "ROUTE_ALL_TRACKS_SCREEN"
    }

}

