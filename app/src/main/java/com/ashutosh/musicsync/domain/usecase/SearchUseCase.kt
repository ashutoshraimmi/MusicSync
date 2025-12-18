package com.ashutosh.musicsync.domain.usecase

import androidx.room.Query
import com.ashutosh.musicsync.domain.model.SearchResult
import com.ashutosh.musicsync.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String) : SearchResult  =
        repository.searchAndCache(query)
}