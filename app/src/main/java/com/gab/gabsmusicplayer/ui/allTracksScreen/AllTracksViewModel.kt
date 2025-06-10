package com.gab.gabsmusicplayer.ui.allTracksScreen

import androidx.lifecycle.ViewModel
import com.gab.gabsmusicplayer.data.repository.MusicRepositoryImpl
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AllTracksViewModel @Inject constructor(
    private val repository: MusicRepositoryImpl
): ViewModel () {

    val tracks = repository.getTracks().map {
        AllTracksScreenState.Tracks(it) as AllTracksScreenState
    }
}