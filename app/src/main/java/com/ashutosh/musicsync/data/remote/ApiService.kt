package com.ashutosh.musicsync.data.remote

import com.ashutosh.musicsync.data.model.SearchListDto
import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api.php")
    suspend fun search(
        @Query("__call") call: String = "autocomplete.get",
        @Query("_format") format: String = "json",
        @Query("_marker") marker: Int = 0,
        @Query("cc") country: String = "in",
        @Query("includeMetaTags") includeMeta: Int = 1,
        @Query("query") query: String
    ): Response<SearchListDto>

    @GET("api.php")
    suspend fun getSongDetails(
        @Query("__call") call: String = "song.getDetails",
        @Query("cc") country: String = "in",
        @Query("_marker") marker: String = "0?_marker=0",
        @Query("_format") format: String = "json",
        @Query("pids") pids: String
    ): retrofit2.Response<String>
}