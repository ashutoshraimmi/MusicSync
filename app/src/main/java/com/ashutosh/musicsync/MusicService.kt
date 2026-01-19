package com.ashutosh.musicsync

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.ashutosh.musicsync.presentation.notification.createMusicNotification
import com.ashutosh.musicsync.presentation.notification.utils.MusicActions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : Service() {

    @Inject
    lateinit var exoPlayer: ExoPlayer
    private var currentSongName = ""
    private var currentArtist = ""

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        // Optional: listen to player state changes
        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                // Update notification when play/pause changes
                updateNotification(isPlaying)
            }
        })
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Start foreground immediately
        startForeground(
            1,
            createMusicNotification(
                this,
                currentSongName.ifEmpty { "Music Player" },
                currentArtist.ifEmpty { "Playing" },
                exoPlayer.isPlaying
            )
        )

        when (intent?.action) {

            MusicActions.ACTION_PLAY -> {
                val url = intent.getStringExtra("URL")
                currentSongName = intent.getStringExtra("SONG_NAME") ?: "Unknown Song"
                currentArtist = intent.getStringExtra("ARTIST") ?: "Unknown Artist"

                if (!url.isNullOrEmpty()) {
                    playSong(url)
                }
            }

            MusicActions.ACTION_PAUSE -> {
                exoPlayer.pause()
                updateNotification(false)
            }

            MusicActions.ACTION_STOP -> {
                stopForeground(true)
                stopSelf()
            }
        }

        return START_STICKY
    }

    private fun playSong(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.play()

        updateNotification(true)
    }


    private fun updateNotification(isPlaying: Boolean) {
        val notification = createMusicNotification(
            context = this,
            songName = currentSongName,
            artist = currentArtist,
            isPlaying = isPlaying
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.notify(1, notification)
    }


    override fun onDestroy() {
        exoPlayer.release()
        super.onDestroy()
    }
}
