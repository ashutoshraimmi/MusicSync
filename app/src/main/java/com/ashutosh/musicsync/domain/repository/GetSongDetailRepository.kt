package com.ashutosh.musicsync.domain.repository

import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong

interface GetSongDetailRepository  {
    suspend fun SearchSong(pid :String) : CurrentSong
    fun CacheCurrentSong() : CurrentSong
}

