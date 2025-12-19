package com.ashutosh.musicsync.navgraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ashutosh.musicsync.presentation.commonui.AppNavItem
import com.ashutosh.musicsync.presentation.commonui.BottomNavItem
import com.ashutosh.musicsync.presentation.components.HeaderType
import com.ashutosh.musicsync.presentation.ui.HomeScreen
import com.ashutosh.musicsync.presentation.ui.PlayerScreen
import com.ashutosh.musicsync.presentation.ui.ProfileScreen
import com.ashutosh.musicsync.presentation.ui.SongListScreen
import com.example.saavn.presentation.ui.SearchScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(onclickCustombar = { songType ->
                navController.navigate(
                    AppNavItem.SongList.createRoute(songType)
                )
            })
        }
        composable(BottomNavItem.Search.route) {
            SearchScreen()
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = AppNavItem.SongList.route,
            arguments = listOf(
                navArgument("songType") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val songType =
                backStackEntry.arguments?.getString("songType") ?: ""

            SongListScreen(
                onBackArrowClick = {
                    navController.popBackStack()
                },
                songType = songType,
                onsongClick = { pids->
                    Log.e("Ashutoshhh", "AppNavGraph: " + pids )
                    navController.navigate(
                        AppNavItem.SongDetails.createRoute(pids)
                    )
                }
            )
        }
        composable(
            route = AppNavItem.SongDetails.route,
            arguments = listOf(
                navArgument("pids") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pids = backStackEntry.arguments?.getString("pids") ?: ""
            Log.e("Ashutoshhh", "AppNavGraph: " + pids)
            PlayerScreen(onBackPress = {
                navController.popBackStack()
            },
                pids = pids
                )

        }

    }
}
