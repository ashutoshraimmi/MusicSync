package com.ashutosh.musicsync.data.mapper

import com.ashutosh.musicsync.data.model.SearchListDto
import com.ashutosh.musicsync.data.model.SongItemDto
import com.ashutosh.musicsync.data.model.SongsDto
import com.ashutosh.musicsync.domain.model.SearchResult
import com.ashutosh.musicsync.domain.model.Song

fun SongItemDto.toDomain(): Song = Song(
    id = id,
    title = title,
    image = image,
    album = album,
    artist = more_info?.primary_artists ?: more_info?.singers ?: "",
    audioUrl = more_info?.vlink ?: url, // prefer vlink then url
    language = more_info?.language ,
    pids = pids?.song_pids ?:""


)

fun SongsDto.toDomain(): List<Song> = data.map { it.toDomain() }

fun SearchListDto.toDomain(): SearchResult =
    SearchResult(
        songs = songs.toDomain()
    )