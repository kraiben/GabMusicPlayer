package com.gab.gabsmusicplayer.ui.screens.main

import androidx.lifecycle.ViewModel
import com.gab.gabsmusicplayer.data.repository.MusicRepositoryImpl

import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MusicRepositoryImpl
) : ViewModel() {

    val tracks = repository.getTracks()

}