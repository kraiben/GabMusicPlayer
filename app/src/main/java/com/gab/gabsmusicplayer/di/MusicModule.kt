package com.gab.gabsmusicplayer.di

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