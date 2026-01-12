package com.ashutosh.musicsync.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
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
    private var exoPlayer: ExoPlayer? = null

    init {
        exoPlayer = ExoPlayer.Builder(context).build()

        exoPlayer?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }
        })
    }
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





    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)

        // Avoid resetting if same song is already loaded
        if (exoPlayer?.currentMediaItem?.localConfiguration?.uri.toString() != url) {
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()
        }

        exoPlayer?.play()
    }


    fun pause() {
        exoPlayer?.pause()
        _isPlaying.value = false
    }

    fun toggleShuffle() {
        val newValue = !_isShuffleOn.value
        _isShuffleOn.value = newValue
        exoPlayer?.shuffleModeEnabled = newValue
    }

    fun togglePlayPause() {
        if (_isPlaying.value) {
            pause()
        } else {
            exoPlayer?.play()
            _isPlaying.value = true
        }
    }
    fun togglePlayPause(url: String) {
        if (_isPlaying.value) {
            pause()
        } else {
            if (exoPlayer == null || exoPlayer?.currentMediaItem == null) {
                play(url)
            } else {
                exoPlayer?.play()
                _isPlaying.value = true
            }
        }
    }


    private val _isSpeedUp = MutableStateFlow(false)
    val isSpeedUp = _isSpeedUp.asStateFlow()

    fun togglePlaybackSpeed() {
        val speedUp = !_isSpeedUp.value
        _isSpeedUp.value = speedUp
        exoPlayer?.setPlaybackSpeed(if (speedUp) 1.25f else 1.0f)
    }
    fun toggleRepeatMode() {
        val newValue = !_isRepeatMode.value
        _isRepeatMode.value = newValue

        exoPlayer?.repeatMode =
            if (newValue) ExoPlayer.REPEAT_MODE_ONE
            else ExoPlayer.REPEAT_MODE_OFF
    }

    override fun onCleared() {
        exoPlayer?.release()
        exoPlayer = null
        super.onCleared()
    }
}
