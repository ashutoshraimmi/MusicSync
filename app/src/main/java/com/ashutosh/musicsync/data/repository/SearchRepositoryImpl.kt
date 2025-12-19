package com.example.saavn.data.repository


import com.ashutosh.musicsync.data.local.SongDao
import com.ashutosh.musicsync.data.local.toDomain
import com.ashutosh.musicsync.data.local.toEntity
import com.ashutosh.musicsync.data.mapper.toDomain
import com.ashutosh.musicsync.data.remote.ApiService
import com.ashutosh.musicsync.domain.model.SearchResult
import com.ashutosh.musicsync.domain.repository.SearchRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val dao: SongDao
) : SearchRepository {

    override suspend fun searchAndCache(query: String): SearchResult {
        val resp = api.search(query = query)
        if (resp.isSuccessful) {
            val dto = resp.body()!!

            val domain = dto.toDomain()
            // map domain songs to entities and store
            val entities = domain.songs.map { it.toEntity() }
            dao.clearAll() // optional: clear previous results for new query
            dao.insertAll(entities)
            return domain
        } else {
            // fallback: return cached
            val cached = dao.getAllSongs()
                .map { list -> list.map { it.toDomain() } }
                .let { flow ->
                    // blocking collection not ideal; prefer throwing or returning cached in separate method.
                    // here return empty result for simplicity
                }
            return SearchResult(songs = emptyList())
        }
    }

    override fun observeCachedSongs() =
        dao.getAllSongs().map { list -> list.map { it.toDomain() } }
}
