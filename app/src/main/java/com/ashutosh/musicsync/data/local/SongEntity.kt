package com.ashutosh.musicsync.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey val id: String,
    val title: String,
    val image: String?,
    val album: String?,
    val artist: String?,
    val audioUrl: String?,
    val language: String?,
    val pids : String?
)
