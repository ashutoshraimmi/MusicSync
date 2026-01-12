package com.ashutosh.musicsync.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.AsyncImage
import com.ashutosh.musicsync.R
import com.ashutosh.musicsync.presentation.viewmodel.PlayerViewModel
import com.ashutosh.musicsync.presentation.viewmodel.SongListViewModel

@Composable
fun PlayerScreen(
    onBackPress : () -> Unit ,
    pids : String,
    viewModel: PlayerViewModel = hiltViewModel()

) {

    val songState = viewModel.currentSong.collectAsState().value
    val isPlaying = viewModel.isPlaying.collectAsState().value
    val isShuffleOn = viewModel.isShuffleOn.collectAsState().value
    val isrepeatOn = viewModel.isRepeatMode.collectAsState().value
    LaunchedEffect(pids) {
        viewModel.getDetails(pids)
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        AsyncImage(
            model = songState?.image,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 🔹 Dark overlay (Spotify-style)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.55f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            // 🔹 TOP BAR (Close button)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    onBackPress()
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Close Player",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.weight(1f))

            // 🔹 Song Info
            Column(
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Row() {
                    Text(
                        text = songState?.song.orEmpty(),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    IconButton(onClick = {
                        viewModel.toggleRepeatMode()
                    }) {
                        if(!isrepeatOn){
                            Icon(
                                painterResource(R.drawable.outline_repeat_one_24),
                                contentDescription = "Repeat one "
                            )
                        }
                        else{
                            Icon(
                                painterResource(R.drawable.outline_repeat_on_24),
                                contentDescription = "Repeat one "
                            )

                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = songState?.primary_artists.orEmpty(),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )

            }

            // 🔹 Player Controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = {
                    viewModel.toggleShuffle()
                }) {
                    if(!isShuffleOn){
                        Icon(
                            painterResource(R.drawable.outline_shuffle_24),
                            contentDescription = "Shuffle"
                        )

                    }else{
                        Icon(
                            painterResource(R.drawable.outline_shuffle_on_24),
                            contentDescription = "Shuffle on"
                        )
                    }
                }
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Previous",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }



                    IconButton(
                        onClick = {
                            songState?.vlink?.let { url ->
                                viewModel.togglePlayPause(url)
                            }
                        }
                    ) {
                        if (isPlaying) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_pause),
                                contentDescription = "Pause",
                                tint = Color.White,
                                modifier = Modifier.size(72.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier.size(72.dp)
                            )
                        }
                    }



                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Next",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                IconButton(onClick = {
                    viewModel.togglePlaybackSpeed()
                }) {
                    Icon(
                        painterResource(R.drawable.outline_timer_24),
                        contentDescription = "Timer"
                    )
                }
            }
        }
    }

}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_MASK)
@Composable
fun PlayerScreenDarkLightPreview() {
    PlayerScreen(
        onBackPress = {

        },
        pids = ""
    )
}

