package com.ashutosh.musicsync.data.model

data class SongMoreInfoDto(
    val vcode: String?,
    val vlink: String?,
    val primary_artists: String?,
    val singers: String?,
    val video_available: Boolean?,
    val triller_available: Boolean?,
    val language: String?
)
