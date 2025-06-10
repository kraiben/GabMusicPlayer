package com.gab.gabsmusicplayer.ui.player

import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import com.gab.gabsmusicplayer.utils.GAB_CHECK

class MusicService : MediaSessionService() {

    private val player: ExoPlayer by lazy {
        val attributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()
        ExoPlayer.Builder(this)
            .setAudioAttributes(attributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build().apply { repeatMode = ExoPlayer.REPEAT_MODE_ALL }
    }
    private val mediaSession by lazy {
        MediaSession.Builder(this, player).setCallback(
            object : MediaSession.Callback {
                @OptIn(UnstableApi::class)
                override fun onConnect(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                ): MediaSession.ConnectionResult {
                    if (session.isMediaNotificationController(controller)) {
                        val sessionCommands =
                            MediaSession.ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                                .add(SessionCommand.COMMAND_CODE_SESSION_SET_RATING)
                                .build()
                        val playerCommands =
                            MediaSession.ConnectionResult.DEFAULT_PLAYER_COMMANDS.buildUpon()
                                .add(ExoPlayer.COMMAND_SEEK_BACK)
                                .add(ExoPlayer.COMMAND_SEEK_TO_PREVIOUS)
                                .add(ExoPlayer.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM)
                                .build()
                        return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
                            .setAvailablePlayerCommands(playerCommands)
                            .setAvailableSessionCommands(sessionCommands)
                            .build()
                    }
                    return MediaSession.ConnectionResult.AcceptedResultBuilder(session).build()
                }
            }
        ).build()
    }
    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession =
        mediaSession

    override fun onDestroy() {
        mediaSession.run {
            player.release()
            release()
        }
        GAB_CHECK("SERVICE DESTROYED")
        super.onDestroy()
    }


}