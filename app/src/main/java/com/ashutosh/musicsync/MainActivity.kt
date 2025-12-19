package com.ashutosh.musicsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ashutosh.musicsync.navgraph.AppNavGraph
import com.ashutosh.musicsync.presentation.commonui.BottomBar
import com.ashutosh.musicsync.ui.theme.MusicSyncTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MusicSyncTheme {
                MusicSyncApp()
            }
        }
    }
}


@Composable
fun MusicSyncApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

