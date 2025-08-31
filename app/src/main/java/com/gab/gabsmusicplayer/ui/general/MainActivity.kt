package com.gab.gabsmusicplayer.ui.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gab.feature_all_tracks.holder.AllTTracksFeatureHolder
import com.gab.gabsmusicplayer.ui.MusicApplication
import com.gab.gabsmusicplayer.ui.navigation.rememberNavigationState
import com.gab.gabsmusicplayer.ui.theme.GabsMusicPlayerTheme
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var allTracksFeatureHolder: AllTTracksFeatureHolder
    private val component by lazy {
        (application as MusicApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        GAB_CHECK("___________________________________________________________________________")
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
//            val list = remember { mutableStateOf((0..100).map { it -> (it to "Element $it") }.toList()) }
//            LazyColumn(modifier = Modifier.background(color = Color(0xFFFFFFFF)).fillMaxSize()) {
//                items(list.value, key = {it.first}) {el ->
//                    Text(modifier = Modifier.fillMaxWidth().height(50.dp), text = el.second, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//                }
//            }
            GabsMusicPlayerTheme {
                NavHost(
                    rememberNavigationState().navHostController,
                    allTracksFeatureHolder.getAllTracksScreenRoute()
                ) {
                    with(allTracksFeatureHolder) {
                        allTracksScreen(
                            modifier = Modifier.fillMaxSize(),
                            {},
                            {s ->},
                            {t, d -> })
                    }
                }
            }
//            MusicMainScreen(viewModelFactory)
        }
    }
}
