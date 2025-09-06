package com.gab.core_media.impl

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.SessionCommand
import com.gab.core_media.GAB_CHECK

public class MusicService : MediaSessionService() {

    private val player: ExoPlayer by lazy {
        val attributes = AudioAttributes.Builder().setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC).build()
        ExoPlayer.Builder(this).setAudioAttributes(attributes, true)
            .setHandleAudioBecomingNoisy(true).build()
            .apply { repeatMode = ExoPlayer.REPEAT_MODE_ALL }
    }
    private val mediaSession: MediaSession by lazy {
        MediaSession.Builder(this, player).setCallback(
            @UnstableApi object : MediaSession.Callback {
                private var clickCount = 0
                private val handler = Handler(Looper.getMainLooper())
                private val MULTI_CLICK_TIMEOUT = 300L
                override fun onMediaButtonEvent(
                    session: MediaSession,
                    controllerInfo: MediaSession.ControllerInfo,
                    intent: Intent,
                ): Boolean {
                    val keyEvent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
                    } else {
                        intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                    }
                    if (keyEvent?.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_HEADSETHOOK) {
                        clickCount++
                        handler.removeCallbacksAndMessages(null)

                        fun handleSingleClick() {
                            if (player.isPlaying) {
                                player.pause()
                            } else {
                                player.play()
                            }
                        }

                        fun handleDoubleClick() {
                            player.seekToNext()
                        }

                        fun handleTripleClick() {
                            player.seekToPrevious()
                        }

                        when (clickCount) {
                            1 -> handler.postDelayed({
                                if (clickCount == 1) {
                                    handleSingleClick()
                                }
                                clickCount = 0
                            }, MULTI_CLICK_TIMEOUT)

                            2 -> {
                                handler.postDelayed({
                                    if (clickCount == 2) {
                                        handleDoubleClick()
                                    }
                                    clickCount = 0
                                }, MULTI_CLICK_TIMEOUT)
                            }

                            3 -> {
                                handleTripleClick()
                                clickCount = 0
                            }
                        }
                        return true
                    }
                    return super.onMediaButtonEvent(
                        session, controllerInfo, intent
                    )
                }

                @OptIn(UnstableApi::class)
                override fun onConnect(
                    session: MediaSession,
                    controller: MediaSession.ControllerInfo,
                ): MediaSession.ConnectionResult {
                    if (session.isMediaNotificationController(controller)) {
                        val sessionCommands =
                            MediaSession.ConnectionResult.DEFAULT_SESSION_COMMANDS.buildUpon()
                                .add(SessionCommand.COMMAND_CODE_SESSION_SET_RATING).build()
                        val playerCommands =
                            MediaSession.ConnectionResult.DEFAULT_PLAYER_COMMANDS.buildUpon()
                                .add(ExoPlayer.COMMAND_SEEK_BACK).add(ExoPlayer.COMMAND_STOP)
                                .add(ExoPlayer.COMMAND_PLAY_PAUSE)
                                .add(ExoPlayer.COMMAND_SEEK_TO_PREVIOUS)
                                .add(ExoPlayer.COMMAND_SEEK_TO_PREVIOUS_MEDIA_ITEM).build()
                        return MediaSession.ConnectionResult.AcceptedResultBuilder(session)
                            .setAvailablePlayerCommands(playerCommands)
                            .setAvailableSessionCommands(sessionCommands).build()
                    }
                    return MediaSession.ConnectionResult.AcceptedResultBuilder(session).build()
                }
            }).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession
    }

    init {
        GAB_CHECK("MusicService init Started")
    }
    override fun onCreate() {
        GAB_CHECK("MusicService onCreate Started")
        super.onCreate()
        GAB_CHECK("MusicService onCreate finished")

    }
    override fun onDestroy() {
        mediaSession.run {
            player.release()
            release()
        }
        super.onDestroy()
    }
}