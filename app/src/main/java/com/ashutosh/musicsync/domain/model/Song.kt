package com.ashutosh.musicsync.domain.model


data class Song(
    val id: String,
    val title: String,
    val image: String?,
    val album: String?,
    val artist: String?,
    val audioUrl: String?,
    val language: String?
)
