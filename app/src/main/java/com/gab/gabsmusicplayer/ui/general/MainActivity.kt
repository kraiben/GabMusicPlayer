package com.gab.gabsmusicplayer.ui.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gab.gabsmusicplayer.ui.MusicApplication
import com.gab.gabsmusicplayer.ui.ViewModelFactory
import com.gab.gabsmusicplayer.ui.screens.main.MusicMainScreen
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
        enableEdgeToEdge()
        setContent {MusicMainScreen(viewModelFactory) }
    }

}

@Composable
fun Test() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(Color.Red)
            .padding(16.dp)
            .background(Color.Green)
    )
}