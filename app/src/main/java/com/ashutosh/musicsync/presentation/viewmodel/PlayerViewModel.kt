package com.ashutosh.musicsync.presentation.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl
import androidx.media3.exoplayer.ExoPlayer
import com.ashutosh.musicsync.MusicService
import com.ashutosh.musicsync.domain.model.Song
import com.ashutosh.musicsync.domain.model.currentSong.CurrentSong
import com.ashutosh.musicsync.domain.usecase.GetSongsDetailUseCase
import com.ashutosh.musicsync.domain.usecase.SearchUseCase
import com.ashutosh.musicsync.presentation.notification.utils.MusicActions
import com.ashutosh.musicsync.presentation.ui.startMusicService
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

/**
 * Helper function to create a basic CurrentSong from Song data
 * This provides instant UI feedback while full details load
 */
private fun Song.toBasicCurrentSong(): CurrentSong = CurrentSong(
    id = id,
    song = title,
    primary_artists = artist,
    image = image,
    album = album,
    language = language,
    vlink = audioUrl,
    type = null, year = null, music = null, music_id = null,
    primary_artists_id = null, featured_artists = null, featured_artists_id = null,
    singers = null, starring = null, label = null, albumid = null,
    origin = null, play_count = null, is_drm = null, copyright_text = null,
    is320kbps = null, is_dolby_content = null, explicit_content = null,
    has_lyrics = null, lyrics_snippet = null, encrypted_drm_media_url = null,
    encrypted_media_url = null, encrypted_media_path = null, media_preview_url = null,
    perma_url = null, album_url = null, duration = null, rights = null,
    webp = null, cache_state = null, starred = null, artistMap = null,
    release_date = null, vcode = null, triller_available = null,
    label_url = null, label_id = null
)

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val getSongsDetailUseCase: GetSongsDetailUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _currentSong = MutableStateFlow<CurrentSong?>(null)
    val currentSong: StateFlow<CurrentSong?> = _currentSong

    // Queue management
    private val _queue = MutableStateFlow<List<Song>>(emptyList())
    val queue: StateFlow<List<Song>> = _queue.asStateFlow()

    private var currentQueueIndex = -1

    private var job: Job? = null
    private var exoPlayer: ExoPlayer? = null

    init {
        exoPlayer = ExoPlayer.Builder(context).build()

        exoPlayer?.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                // When a song ends and next starts playing automatically
                if (reason == Player.MEDIA_ITEM_TRANSITION_REASON_AUTO ||
                    reason == Player.MEDIA_ITEM_TRANSITION_REASON_SEEK) {
                    val currentIndex = exoPlayer?.currentMediaItemIndex ?: -1
                    if (currentIndex >= 0 && currentIndex < _queue.value.size) {
                        currentQueueIndex = currentIndex
                        val song = _queue.value[currentIndex]

                        // Immediately update UI with basic song info from queue
                        // This provides instant feedback while we load full details
                        _currentSong.value = song.toBasicCurrentSong()

                        // Load full details in background
                        song.pids?.let { pid ->
                            if (pid.isNotBlank()) {
                                viewModelScope.launch {
                                    try {
                                        delay(100) // Small delay to avoid race conditions
                                        getDetails(pid)
                                    } catch (e: Exception) {
                                        Log.e("Ashutosh", "Failed to load song details: ${e.message}", e)
                                        // Keep the basic info we set above, don't clear it
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                // Handle end of playback
                if (playbackState == Player.STATE_ENDED) {
                    // If repeat mode is off and we're at the last song, stop
                    if (!_isRepeatMode.value && currentQueueIndex >= _queue.value.size - 1) {
                        _isPlaying.value = false
                    }
                }
            }
        })
    }
    fun getDetails(pid: String) {
        if (pid.isBlank()) {
            Log.e("Ashutoshh", "getDetails: Empty pid, skipping")
            return
        }

        job?.cancel()
        job = viewModelScope.launch {
            try {
                delay(300)
                val result = getSongsDetailUseCase(pid)
                Log.e("Ashutoshh", "getDetails: $result")
                _currentSong.value = result
            } catch (e: Exception) {
                Log.e("Ashutoshh", "getDetails error: ${e.message}", e)
                // Don't update currentSong if there's an error - keep the previous one
                // This prevents UI from breaking when API calls fail
            }
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





    /**
     * Set the queue of songs to play
     */
    fun setQueue(songs: List<Song>) {
        _queue.value = songs
        currentQueueIndex = -1

        // Build MediaItem list from queue
        // Use audioUrl if available, otherwise we'll need to fetch vlink later
        val mediaItems = songs.mapNotNull { song ->
            song.audioUrl?.let { url ->
                MediaItem.Builder()
                    .setUri(url)
                    .setMediaId(song.id)
                    .build()
            }
        }

        if (mediaItems.isNotEmpty()) {
            // Set media items without resetting position or starting playback
            exoPlayer?.setMediaItems(mediaItems, 0, 0) // startIndex=0, startPositionMs=0
            exoPlayer?.prepare()
            // Set initial repeat mode based on current state
            exoPlayer?.repeatMode = if (_isRepeatMode.value) {
                ExoPlayer.REPEAT_MODE_ONE
            } else {
                ExoPlayer.REPEAT_MODE_ALL // Will play through queue, we'll handle stopping at end
            }
        }
    }

    /**
     * Play a specific song from the queue by index
     */
    fun playFromQueue(index: Int) {
        val queue = _queue.value
        if (index >= 0 && index < queue.size) {
            currentQueueIndex = index
            val song = queue[index]

            // First, seek ExoPlayer to the correct position in the queue
            val mediaItemCount = exoPlayer?.mediaItemCount ?: 0
            if (mediaItemCount > index) {
                exoPlayer?.seekTo(index, 0)
                exoPlayer?.play()
                _isPlaying.value = true
            } else {
                // If queue not set yet, play directly
                song.audioUrl?.let { url ->
                    play(url)
                }
            }

            // Immediately update UI with basic song info from queue
            // This provides instant feedback while we load full details
            _currentSong.value = song.toBasicCurrentSong()

            // Load song details in background (for UI display)
            song.pids?.let { pid ->
                job?.cancel()
                job = viewModelScope.launch {
                    try {
                        delay(100) // Small delay to avoid race conditions
                        val result = getSongsDetailUseCase(pid)
                        // Only update if we got a valid result
                        if (result != null) {
                            _currentSong.value = result

                            // If we got a better URL (vlink), update the MediaItem
                            result.vlink?.let { vlink ->
                                if (mediaItemCount > index) {
                                    val updatedMediaItem = MediaItem.Builder()
                                        .setUri(vlink)
                                        .setMediaId(song.id)
                                        .build()
                                    exoPlayer?.replaceMediaItem(index, updatedMediaItem)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("Ashutosh", "Failed to load full song details: ${e.message}", e)
                        // Keep the basic info we set above, don't clear it
                    }
                }
            }
        }
    }

    /**
     * Play a song by its ID from the queue
     */
    fun playSongById(songId: String) {
        val queue = _queue.value
        val index = queue.indexOfFirst { it.id == songId }
        if (index >= 0) {
            playFromQueue(index)
        } else {
            // If song not in queue, try to play directly
            val song = queue.firstOrNull { it.id == songId }
            song?.pids?.let { pid ->
                getDetails(pid)
                song.audioUrl?.let { url ->
                    play(url)
                }
                if (!song.audioUrl.isNullOrEmpty()) {
                    fun startMusicService(
                        context: Context,
                        url: String,
                        song: String,
                        artist: String
                    ) {
                        val intent = Intent(context, MusicService::class.java).apply {
                            action = MusicActions.ACTION_PLAY
                            putExtra("URL", url)
                            putExtra("SONG_NAME", song)
                            putExtra("ARTIST", artist)
                        }

                        ContextCompat.startForegroundService(context, intent)
                    }

                } else {
                    Log.e("SongListScreen", "Audio URL is null for song: ${song.title}")
                }
            }
        }
    }

    fun play(url: String) {
        val mediaItem = MediaItem.fromUri(url)

        // Avoid resetting if same song is already loaded
        if (exoPlayer?.currentMediaItem?.localConfiguration?.uri.toString() != url) {
            exoPlayer?.setMediaItem(mediaItem)
            exoPlayer?.prepare()
        }

        exoPlayer?.play()
    }

    /**
     * Play next song in queue
     */
    fun playNext() {
        val queue = _queue.value
        if (queue.isEmpty()) return

        val nextIndex = if (currentQueueIndex < 0) {
            0
        } else {
            (currentQueueIndex + 1).coerceAtMost(queue.size - 1)
        }

        if (nextIndex < queue.size) {
            playFromQueue(nextIndex)
        }
    }

    /**
     * Play previous song in queue
     */
    fun playPrevious() {
        val queue = _queue.value
        if (queue.isEmpty()) return

        val prevIndex = if (currentQueueIndex <= 0) {
            0
        } else {
            currentQueueIndex - 1
        }

        playFromQueue(prevIndex)
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
            if (newValue) {
                ExoPlayer.REPEAT_MODE_ONE // Repeat current song
            } else {
                ExoPlayer.REPEAT_MODE_ALL // Continue to next song in queue
            }
    }

    override fun onCleared() {
        exoPlayer?.release()
        exoPlayer = null
        super.onCleared()
    }
}
