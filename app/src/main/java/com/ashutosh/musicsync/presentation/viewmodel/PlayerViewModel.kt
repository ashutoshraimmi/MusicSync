package com.ashutosh.musicsync.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl
import androidx.media3.exoplayer.ExoPlayer
import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong
import com.ashutosh.musicsync.domain.usecase.GetSongsDetailUseCase
import com.ashutosh.musicsync.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getSongsDetailUseCase: GetSongsDetailUseCase,
    @ApplicationContext private val context: Context
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
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()
    private val _isShuffleOn = MutableStateFlow(false)
    val isShuffleOn = _isShuffleOn.asStateFlow()

    private val _timer = MutableStateFlow(true)
    val timer = _timer.asStateFlow()

    private val _isRepeatMode = MutableStateFlow(false)
    val isRepeatMode = _isRepeatMode.asStateFlow()




    private var exoPlayer: ExoPlayer? = null

    fun play(url: String) {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).setLivePlaybackSpeedControl(
                DefaultLivePlaybackSpeedControl.Builder().setFallbackMaxPlaybackSpeed(1.5f).build()
            ).build()
        }

        val mediaItem = MediaItem.fromUri(url)
        exoPlayer?.apply {
            setMediaItem(mediaItem)
            prepare()
            play()
        }

        _isPlaying.value = true
    }

    fun pause() {
        exoPlayer?.pause()
        _isPlaying.value = false
    }

    fun toggleShuffle(){
        exoPlayer?.shuffleModeEnabled = _isShuffleOn.value
        _isShuffleOn.value = !_isShuffleOn.value

    }
    fun togglePlayPause(url: String) {
        if (_isPlaying.value) {
            pause()
        } else {
            play(url)
        }
    }
    fun playbackSpeed(){
        exoPlayer?.setPlaybackSpeed(1.25f)
        _timer.value = !_timer.value

    }
    fun toggleRepeatMode(){
        exoPlayer?.repeatMode
        _isRepeatMode.value = !_isRepeatMode.value
    }
    override fun onCleared() {
        exoPlayer?.release()
        exoPlayer = null
        super.onCleared()
    }
}
