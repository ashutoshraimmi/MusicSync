package com.ashutosh.musicsync.domain.usecase

import com.ashutosh.musicsync.domain.repository.GetSongDetailRepository
import javax.inject.Inject

class GetSongsDetailUseCase @Inject constructor(private val repository: GetSongDetailRepository) {
    suspend operator fun invoke(pids : String) =
        repository.SearchSong(pids)

}