package com.gab.gabsmusicplayer.di

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import dagger.Module
import dagger.Provides

//@Module
//interface MusicModule {
//
//    companion object {
//
//        @ApplicationScope
//        @Provides
//        fun providePlayer(context: Context): ExoPlayer {
//
//            val attributes = AudioAttributes.Builder()
//                .setUsage(C.USAGE_MEDIA)
//                .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
//                .build()
//            return ExoPlayer.Builder(context)
//                .setAudioAttributes(attributes, true)
//                .setHandleAudioBecomingNoisy(true)
//                .build()
//        }
//    }
//}