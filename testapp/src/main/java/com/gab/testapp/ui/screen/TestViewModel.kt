package com.gab.testapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository
import javax.inject.Inject
import javax.inject.Provider

class TestViewModel(
    private val mediaPlayerRepository: MediaPlayerRepository,
    private val tracksListRepository: TracksListRepository,
) : ViewModel() {
    val isInitialized = mediaPlayerRepository.isInitialized()

    class Factory @Inject constructor(
        private val mediaPlayerRepository: Provider<MediaPlayerRepository>,
        private val tracksListRepository: Provider<TracksListRepository>,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TestViewModel::class.java)
            return TestViewModel(
                mediaPlayerRepository.get(),
                tracksListRepository.get()
            ) as T
        }
    }
}