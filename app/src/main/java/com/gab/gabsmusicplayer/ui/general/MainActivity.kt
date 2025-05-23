package com.gab.gabsmusicplayer.ui.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gab.gabsmusicplayer.ui.MusicApplication
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.screens.main.MusicMainScreen
import com.gab.gabsmusicplayer.ui.theme.GabsMusicPlayerTheme
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as MusicApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        GAB_CHECK("___________________________________________________________________________")
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            GabsMusicPlayerTheme {
                Surface(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
                    MusicMainScreen(viewModelFactory)
                }
            }

        }
    }

}

@Composable
fun ThemeDebugView() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.primary))
}