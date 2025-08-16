package com.gab.core_music_loading.api.di

import com.gab.core_music_loading.api.data.MusicLoadingRepository

internal interface MusicLoadingApi {
    fun musicLoadingRepository(): MusicLoadingRepository
}