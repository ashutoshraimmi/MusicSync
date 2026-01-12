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
import androidx.compose.runtime.getValue
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

    val currentSong by playerViewModel.currentSong.collectAsState()

    Scaffold(
        bottomBar = {
            Column {

                // ✅ Mini Player (only when song exists)
                if (currentSong != null) {
                    MiniPlayerComponent(viewModel = playerViewModel)
                }

                // ✅ Bottom Navigation Bar
                BottomBar(navController = navController)
            }
        }
    ) { paddingValues ->

        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues),
            playerViewModel = playerViewModel   // 🔥 SAME INSTANCE
        )
    }
}
