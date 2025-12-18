package com.ashutosh.musicsync.data.model

data class AlbumItemDto(
    val id: String,
    val title: String,
    val image: String,
    val music: String,
    val url: String,
    val type: String,
    val description: String,
    val ctr: Int,
    val position: Int,
    val more_info: AlbumMoreInfoDto
)
