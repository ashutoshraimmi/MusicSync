package com.ashutosh.musicsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ashutosh.musicsync.presentation.commonui.BottomBar
import com.ashutosh.musicsync.presentation.commonui.BottomNavItem
import com.ashutosh.musicsync.presentation.ui.HomeScreen
import com.ashutosh.musicsync.presentation.ui.ProfileScreen
import com.ashutosh.musicsync.presentation.viewmodel.SearchViewModel
import com.ashutosh.musicsync.ui.theme.MusicSyncTheme
import com.example.saavn.presentation.ui.SearchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicSyncTheme {
                    MainScreen()

            }
        }
    }
}

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) {
        paddingValues ->
        NavHost(navController = navController, startDestination = BottomNavItem.Home.route , modifier = Modifier.padding(paddingValues) ){
            composable(BottomNavItem.Home.route){
                HomeScreen()
            }
            composable(BottomNavItem.Search.route) {
                SearchScreen()
            }
            composable(BottomNavItem.Profile.route){
                ProfileScreen()
            }
        }
    }
}
