package com.ashutosh.musicsync.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong
import com.ashutosh.musicsync.domain.usecase.GetSongsDetailUseCase
import com.ashutosh.musicsync.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getSongsDetailUseCase: GetSongsDetailUseCase
) : ViewModel() {

    private val _currentSong = MutableStateFlow<CurrentSong?>(null)
    val currentSong: StateFlow<CurrentSong?> = _currentSong

    private var job: Job? = null

    fun getDetails(pid: String) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(300)
            val result = getSongsDetailUseCase(pid)
            Log.e("Ashutoshh", "getDetails: $result")
            _currentSong.value = result
        }
    }
}
