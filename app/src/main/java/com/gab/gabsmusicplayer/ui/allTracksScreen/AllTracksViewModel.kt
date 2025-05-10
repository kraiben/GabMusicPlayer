package com.gab.gabsmusicplayer.ui.allTracksScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gab.gabsmusicplayer.data.repository.MusicRepositoryImpl
import com.gab.gabsmusicplayer.utils.GAB_CHECK
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllTracksViewModel @Inject constructor(
    private val repository: MusicRepositoryImpl
): ViewModel () {

    val tracks = repository.getTracks().map {
        AllTracksScreenState.Tracks(it) as AllTracksScreenState
    }

    init {
        GAB_CHECK("")
        viewModelScope.launch {
            tracks.collect{
                GAB_CHECK(it)
            }
        }
    }
}