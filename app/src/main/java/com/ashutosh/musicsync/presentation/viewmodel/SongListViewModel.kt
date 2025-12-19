package com.ashutosh.musicsync.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.musicsync.domain.model.Song
import com.ashutosh.musicsync.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongListViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel() {
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs
    private var searchJob: Job? = null
    fun onQueryChanged(query: String) {
        // simple debounce
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // debounce 300ms
            if (query.isBlank()) {
                _songs.value = emptyList()
                return@launch
            }
            val result = searchUseCase(query)
            val selectedSongs = result.songs
//                .filter {song ->
//                song.language?.trim()?.equals("hindi", ignoreCase = true) == true
//            }
//            if(selectedSongs.isEmpty()){
//
//            }

            Log.e("Ashutosh", "onQueryChanged: " + selectedSongs )
            _songs.value = selectedSongs
        }
    }


}