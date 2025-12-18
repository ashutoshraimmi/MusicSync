package com.ashutosh.musicsync.domain.repository

import com.ashutosh.musicsync.domain.model.SearchResult
import com.ashutosh.musicsync.domain.model.Song
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchAndCache(query: String): SearchResult
    fun observeCachedSongs(): Flow<List<Song>>
}