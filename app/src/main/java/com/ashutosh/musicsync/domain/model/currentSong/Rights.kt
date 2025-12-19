package com.ashutosh.musicsync.domain.model.currentSong

data class Rights(
    val code: Int?,
    val reason: String?,
    val cacheable: Boolean?,
    val delete_cached_object: Boolean?
)
