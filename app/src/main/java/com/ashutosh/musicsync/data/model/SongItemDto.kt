package com.ashutosh.musicsync.data.model

data class SongItemDto(
    val id: String,
    val title: String,
    val image: String,
    val album: String,
    val url: String,
    val type: String,
    val description: String,
    val ctr: Int,
    val position: Int,
    val more_info: SongMoreInfoDto
)
