package com.ashutosh.musicsync.presentation.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ashutosh.musicsync.MusicService
import com.ashutosh.musicsync.presentation.components.CustomHeader
import com.ashutosh.musicsync.presentation.components.CustomSearchBar
import com.ashutosh.musicsync.presentation.components.HeaderType
import com.ashutosh.musicsync.presentation.components.MusicListTile
import com.ashutosh.musicsync.presentation.notification.utils.MusicActions
import com.ashutosh.musicsync.presentation.viewmodel.PlayerViewModel
import com.ashutosh.musicsync.presentation.viewmodel.SongListViewModel
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListScreen(
    onBackArrowClick: () -> Unit,
    songType: String,
    onsongClick: (pids: String) -> Unit,
    viewModel: SongListViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf(songType) }
    val context = LocalContext.current   // 🔥 ADD THIS LINE

    // ✅ Collect songs from ViewModel
    val musicList by viewModel.songs.collectAsState()

    // ✅ Trigger search only when query changes
    LaunchedEffect(query) {
        viewModel.onQueryChanged(query)
    }
    
    // ✅ Set queue when songs are loaded
    LaunchedEffect(musicList) {
        if (musicList.isNotEmpty()) {
            playerViewModel.setQueue(musicList)
        }
    }
    val roundedShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 16.dp,
        bottomEnd = 16.dp
    )
    val smallroundedShape = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp, 0.dp),
    ) {
        item {
            CustomHeader(HeaderType.SongList, onarrowClick = {
                onBackArrowClick()

            })
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            CustomSearchBar(
                query = query,
                onInputSearch = { newQuery ->
                    query = newQuery
                })
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "imageUrl",
                    contentDescription = "Song Image",
                    modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth(0.80f)
                        .background(color = Color.Gray, shape = roundedShape)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text(songType, fontSize = 16.sp, color = Color.Black)
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Text("About", fontSize = 16.sp, color = Color.Black)
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }

            item {
                Row() {
                    AsyncImage(
                        model = "imageUrl",
                        contentDescription = "Image Album",
                        modifier = Modifier
                            .height(50.dp)
                            .width(40.dp)
                            .background(color = Color.Gray, shape = smallroundedShape),

                        )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .padding()
                            .background(color = Color.Gray, shape = CircleShape),
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .padding(2.dp),
                            contentDescription = "Add Icon"
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .padding()
                            .background(color = Color.Gray, shape = CircleShape),
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .padding(2.dp),
                            contentDescription = "Add Icon"
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .padding()
                            .background(color = Color.Gray, shape = CircleShape),
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .padding(2.dp),
                            contentDescription = "Add Icon"
                        )
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(45.dp)
                            .padding()
                            .background(color = Color.Gray, shape = CircleShape)
                            .clickable {
                                // Play first song in queue
                                if (musicList.isNotEmpty()) {
                                    playerViewModel.playFromQueue(0)
                                }
                            },
                        contentAlignment = Alignment.Center,

                        ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                                .padding(2.dp),
                            contentDescription = "Play Icon"
                        )
                    }

                }

            }
            item {
                Spacer(modifier = Modifier.padding(8.dp))

            }
            items(
                items = musicList,
                key = { it.id },
            ) { song ->
                Log.e("AShutoshhh", "SongListScreen: " + Gson().toJson(song))
                MusicListTile(
                    title = song.title,
                    subTitle = song.artist.orEmpty(),
                    onclick = {
                        Log.e("Ashutoshh", "SongListScreen: " + song?.audioUrl)
                        // Play the song from queue by ID
                        playerViewModel.playSongById(song.id)
                        // Also navigate to player screen
                        onsongClick(song.id)


                    }
                )
            }

        }
    }

fun startMusicService(
    context: Context,
    songUrl: String,
    songName: String,
    artist: String
) {
    val intent = Intent(context, MusicService::class.java).apply {
        action = MusicActions.ACTION_PLAY
        putExtra("URL", songUrl)
        putExtra("SONG_NAME", songName)
        putExtra("ARTIST", artist)
    }

    ContextCompat.startForegroundService(context, intent)
}

@Preview
@Composable
fun PreviewSongListScreen() {
    SongListScreen(onBackArrowClick = {

    }, songType = "", onsongClick = {

    })
}