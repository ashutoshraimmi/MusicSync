package com.ashutosh.musicsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import androidx.navigation.compose.rememberNavController
import com.ashutosh.musicsync.navgraph.AppNavGraph
import com.ashutosh.musicsync.presentation.commonui.BottomBar
import com.ashutosh.musicsync.presentation.components.MiniPlayerComponent
import com.ashutosh.musicsync.presentation.viewmodel.PlayerViewModel
import com.ashutosh.musicsync.ui.theme.MusicSyncTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusicSyncTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    var isPlaying  = playerViewModel.isPlaying.collectAsState().value

    Scaffold(
        bottomBar = {
            Column {
                // ✅ Show Mini Player only when music is playing
                if (isPlaying) {
                    MiniPlayerComponent(
                        isPlaying = isPlaying,
                        onPlayPauseClick = {
                            playerViewModel.togglePlayPause()
                        }
                    )
                }

                BottomBar(navController = navController)
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}