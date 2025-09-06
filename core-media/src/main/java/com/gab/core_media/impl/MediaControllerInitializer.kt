package com.gab.core_media.impl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import javax.inject.Inject

public class MediaControllerInitializer @Inject constructor(private val context: Context) {

    internal fun initializePlayer(
        onMediaControllerInitialized: (ListenableFuture<MediaController>) -> Unit
    ) {
        val intent = Intent(context, MusicService::class.java)
        ContextCompat.startForegroundService(context, intent)
        val sessionToken = SessionToken(
            context,
            ComponentName(context, MusicService::class.java)
        )
        val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFuture.addListener({
            onMediaControllerInitialized(controllerFuture)
        }, ContextCompat.getMainExecutor(context))
    }
}