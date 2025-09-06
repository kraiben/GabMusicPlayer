package com.gab.gabsmusicplayer.ui.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.gab.feature_all_tracks.holder.AllTTracksFeatureHolder
import com.gab.feature_options_menus.holder.TrackOptionsMenuFeatureHolder
import com.gab.feature_playlists.holder.PlaylistFeatureHolder
import com.gab.gabsmusicplayer.ui.MusicApplication
import com.gab.gabsmusicplayer.ui.theme.GabsMusicPlayerTheme
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.gab.music_entities_module.PlaylistInfoModel
import com.gab.music_entities_module.TrackInfoModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var allTracksFeatureHolder: AllTTracksFeatureHolder

    @Inject
    lateinit var trackOptionsMenuFeatureHolder: TrackOptionsMenuFeatureHolder

    @Inject
    lateinit var playlistFeatureHolder: PlaylistFeatureHolder
    private val component by lazy {
        (application as MusicApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        GAB_CHECK("___________________________________________________________________________")
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            GabsMusicPlayerTheme {
                val navHostController = rememberNavController()
                NavHost(
                    navHostController,
                    allTracksFeatureHolder.getAllTracksScreenRoute()
                ) {
                    with(allTracksFeatureHolder) {
                        allTracksScreen(
                            modifier = Modifier.fillMaxSize(),
                            {},
                            { s -> },
                            { t, d ->
                                trackOptionsMenuFeatureHolder.AllTracksScreenOptionsMenu(
                                    track = t, onDismiss = d
                                )
                            }
                        )
                    }
                    with(playlistFeatureHolder) {
                        playlistsFeatureNavGraph(
                            navHostController = navHostController,
                            modifier = Modifier.fillMaxSize(),
                            viewModelFactory = viewModelFactory,
                            trackOptionsMenuContent = {
                                t: TrackInfoModel, p: PlaylistInfoModel, onDismiss: () -> Unit ->
                                trackOptionsMenuFeatureHolder.PlaylistScreenMenu(
                                    track = t,
                                    playlist = p,
                                    ondDismiss = onDismiss
                                )
                            },
                            pictureSelectingErrorMsg = {s -> }
                        )
                    }
                }
            }
        }
    }
}
