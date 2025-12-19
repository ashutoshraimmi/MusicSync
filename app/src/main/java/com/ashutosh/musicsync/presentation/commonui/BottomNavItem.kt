package com.ashutosh.musicsync.presentation.commonui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Query
import androidx.room.util.query

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Search : BottomNavItem("search", "Search", Icons.Default.Search)
    object Profile : BottomNavItem("profile", "Profile", Icons.Default.Person)
}
object AppNavItem {
    object SongList {
        const val route = "song_list/{songType}"
        fun createRoute(songType: String) = "song_list/$songType"
    }
    object SongDetails {
        const val  route = "song_detail/{pids}"
        fun createRoute(pids : String) = "song_detail/${pids}"
    }
}
