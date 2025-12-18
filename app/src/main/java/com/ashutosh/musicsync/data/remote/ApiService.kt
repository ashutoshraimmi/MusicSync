package com.ashutosh.musicsync.data.remote

import com.ashutosh.musicsync.data.model.SearchListDto
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
}