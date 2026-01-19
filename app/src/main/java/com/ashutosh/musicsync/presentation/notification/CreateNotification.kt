package com.ashutosh.musicsync.presentation.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.ashutosh.musicsync.MusicService
import com.ashutosh.musicsync.R
import com.ashutosh.musicsync.presentation.notification.utils.MusicActions
fun createMusicNotification(
    context: Context,
    songName: String,
    artist: String,
    isPlaying: Boolean
): Notification {

    val remoteViews = RemoteViews("com.ashutosh.musicsync", R.layout.notification_music)

    remoteViews.setTextViewText(R.id.txtSong, songName)
    remoteViews.setTextViewText(R.id.txtArtist, artist)

    val playPauseIcon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
    remoteViews.setImageViewResource(R.id.btnPlayPause, playPauseIcon)

    val playPauseIntent = Intent(context, MusicService::class.java).apply {
        action = if (isPlaying) MusicActions.ACTION_PAUSE else MusicActions.ACTION_PLAY
    }

    val playPausePendingIntent = PendingIntent.getService(
        context,
        0,
        playPauseIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    remoteViews.setOnClickPendingIntent(R.id.btnPlayPause, playPausePendingIntent)

    return NotificationCompat.Builder(context, "music_channel")
        .setSmallIcon(R.drawable.ic_app_logo)
        .setCustomContentView(remoteViews)
        .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setOnlyAlertOnce(true)
        .setOngoing(true)
        .build()

}
