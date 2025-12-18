package com.ashutosh.musicsync.data.local

import com.ashutosh.musicsync.domain.model.Song

fun Song.toEntity() = SongEntity(id, title, image, album, artist, audioUrl, language)
fun SongEntity.toDomain() = Song(id, title, image, album, artist, audioUrl, language)