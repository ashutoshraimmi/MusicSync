package com.ashutosh.musicsync.data.repository

import android.util.Log
import com.ashutosh.musicsync.data.remote.ApiService
import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong
import com.ashutosh.musicsync.domain.repository.GetSongDetailRepository
import javax.inject.Inject

class GetSongDetailRepositoryImpl @Inject constructor(
    private val api : ApiService
) : GetSongDetailRepository{
    override suspend fun SearchSong(pids: String): CurrentSong {
        // 1. Call the API
        val resp = api.getSongDetails(pids = pids)
        Log.e("Ashutosh", "SearchSong: " + resp)

        // FIX: The API returns a Map, so we extract the song using the pids key.
        // We use the Elvis operator (?:) to throw an error if the song isn't found.
        return resp[pids] ?: throw Exception("Song not found for ID: $pids")

    }

    override fun CacheCurrentSong(): CurrentSong {
        TODO("Not yet implemented")
    }

}