package com.gab.testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gab.testapp.ui.screen.MainScreen
import com.gab.testapp.ui.screen.TestViewModel
import com.gab.testapp.ui.theme.Gabs_music_playerTheme
import dagger.Lazy
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject lateinit var viewModelFactory: Lazy<TestViewModel.Factory>
    private val component by lazy {
        (application as TestApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContent {
            Gabs_music_playerTheme {
                MainScreen(viewModelFactory.get())
            }
        }
    }
}