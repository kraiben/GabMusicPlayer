package com.gab.gabsmusicplayer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import com.gab.navigation_module.holder.NavigationHolder
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHolder: NavigationHolder
    private val component by lazy {
        (application as MusicApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        GAB_CHECK("___________________________________________________________________________")
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            navigationHolder.GetNavGraph()
        }
    }
}