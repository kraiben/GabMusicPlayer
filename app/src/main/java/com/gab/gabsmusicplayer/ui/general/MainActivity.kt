package com.gab.gabsmusicplayer.ui.general

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
        setContent {
//            val list = remember { mutableStateOf((0..100).map { it -> (it to "Element $it") }.toList()) }
//            LazyColumn(modifier = Modifier.background(color = Color(0xFFFFFFFF)).fillMaxSize()) {
//                items(list.value, key = {it.first}) {el ->
//                    Text(modifier = Modifier.fillMaxWidth().height(50.dp), text = el.second, fontSize = 24.sp, fontWeight = FontWeight.Bold)
//                }
//            }
            MusicMainScreen(viewModelFactory)
        }
    }
}
