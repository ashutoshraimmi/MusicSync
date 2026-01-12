package com.ashutosh.musicsync.data.repository

import android.util.Log
import com.ashutosh.musicsync.data.remote.ApiService
import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong
import com.ashutosh.musicsync.domain.repository.GetSongDetailRepository
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class GetSongDetailRepositoryImpl @Inject constructor(
    private val api : ApiService,
    private val gson: Gson
) : GetSongDetailRepository{
    override suspend fun SearchSong(pids: String): CurrentSong {
        try {
            // 1. Call the API - it returns a Response<String>
            val response = api.getSongDetails(pids = pids)
            
            if (!response.isSuccessful || response.body() == null) {
                throw Exception("API call failed: ${response.code()} ${response.message()}")
            }
            
            var responseBody = response.body()!!
            Log.e("Ashutosh", "SearchSong raw response: $responseBody")

            // 2. Check if the response is a JSON string (double-encoded)
            // If it starts and ends with quotes, it's a string that needs to be unescaped
            if (responseBody.startsWith("\"") && responseBody.endsWith("\"")) {
                // Unescape the JSON string
                responseBody = gson.fromJson(responseBody, String::class.java)
                Log.e("Ashutosh", "SearchSong unescaped response: $responseBody")
            }

            // 3. Parse as JsonElement first to inspect the structure
            val jsonElement: com.google.gson.JsonElement? = try {
                // Try parsing as JsonElement using gson
                gson.fromJson(responseBody, com.google.gson.JsonElement::class.java)
            } catch (e: Exception) {
                Log.e("Ashutosh", "Failed to parse as JsonElement: ${e.message}")
                null
            }

            // 4. Try different parsing strategies
            if (jsonElement != null && jsonElement.isJsonObject) {
                val jsonObject = jsonElement.asJsonObject
                
                // Check if the pids key exists in the response
                if (jsonObject.has(pids)) {
                    val songJson = jsonObject.getAsJsonObject(pids)
                    return gson.fromJson(songJson, CurrentSong::class.java)
                } else {
                    // Try to get the first object if pids key doesn't exist
                    val firstKey = jsonObject.keySet().firstOrNull()
                    if (firstKey != null) {
                        val songJson = jsonObject.getAsJsonObject(firstKey)
                        return gson.fromJson(songJson, CurrentSong::class.java)
                    }
                }
            }
            
            // 5. Fallback: Try parsing as Map directly
            try {
                val mapType = object : TypeToken<Map<String, CurrentSong>>() {}.type
                val songMap: Map<String, CurrentSong> = gson.fromJson(responseBody, mapType)
                
                if (songMap.containsKey(pids)) {
                    return songMap[pids]!!
                } else {
                    val firstSong = songMap.values.firstOrNull()
                    if (firstSong != null) {
                        return firstSong
                    }
                }
            } catch (e: Exception) {
                Log.e("Ashutosh", "Failed to parse as Map: ${e.message}")
            }
            
            // 6. Last resort: Try parsing as direct CurrentSong object
            try {
                return gson.fromJson(responseBody, CurrentSong::class.java)
            } catch (e: Exception) {
                Log.e("Ashutosh", "Failed to parse as CurrentSong: ${e.message}")
            }
            
            throw Exception("Invalid response format: Could not parse song details. Response: ${responseBody.take(200)}")
        } catch (e: Exception) {
            Log.e("Ashutosh", "Error parsing song details: ${e.message}", e)
            throw Exception("Failed to get song details: ${e.message}", e)
        }
    }

    override fun CacheCurrentSong(): CurrentSong {
        TODO("Not yet implemented")
    }

}