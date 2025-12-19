package com.ashutosh.musicsync.presentation.commonui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavHostController){
    val item = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )
    NavigationBar(modifier = Modifier.height(72.dp).padding(0.dp, 0.dp).background(color = Color.Gray)) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        item.forEach { item ->
            NavigationBarItem(selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(item.icon,  contentDescription = item.title)
                },
                label = { Text(item.title) }

            )
        }
    }
}