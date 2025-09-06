package com.gab.core_media.di

import com.gab.core_media.api.MediaPlayerRepository
import com.gab.core_media.api.TracksListRepository
import com.gab.core_media.impl.MediaRepository
import dagger.Binds
import dagger.Module

@Module
public interface CoreMediaModule{

    @Binds
    @MediaScope
    public fun bindMediaPlayerRepository(impl: MediaRepository): MediaPlayerRepository

    @Binds
    @MediaScope
    public fun bindTracksListRepository(impl: MediaRepository): TracksListRepository

//    public companion object {
//        @Provides
//        @MediaScope
//        public fun provideMediaController(context: Context): MediaController {
//            GAB_CHECK(context is Application)
//            var mediaController: MediaController? = null
//            val intent = Intent(context, MusicService::class.java)
//            ContextCompat.startForegroundService(context, intent)
//            val sessionToken = SessionToken(
//                context,
//                ComponentName(context, MusicService::class.java)
//            )
//            val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
//
//            controllerFuture.addListener({
//                GAB_CHECK("point2")
//                mediaController = controllerFuture.get()
//                GAB_CHECK("point3")
//            }, ContextCompat.getMainExecutor(context))
//            Thread.sleep(2000)
//            return mediaController!!
//        }
//    }
}