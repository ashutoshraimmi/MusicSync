package com.ashutosh.musicsync.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ashutosh.musicsync.presentation.commonui.AppNavItem
import com.ashutosh.musicsync.presentation.components.CustomHeader
import com.ashutosh.musicsync.presentation.components.CustomSongBar
import com.ashutosh.musicsync.presentation.components.CustomTextView
import com.ashutosh.musicsync.presentation.components.HeaderType
import com.ashutosh.musicsync.presentation.components.MediumSongTile
import com.ashutosh.musicsync.presentation.components.OnSpotSong
import com.ashutosh.musicsync.presentation.components.SmallSongTile

@Composable
fun HomeScreen(onclickCustombar: (songtype: String) -> Unit) {

    val gridItems = listOf(
        "Old is Gold",
        "Chill Mix",
        "Rishab Rikhiram",
        "Trending Now India",
        "Karan Aujhla",
        "Man's Best Friend",
        "RAP 91",
        "Desi Dan Bolzerian Radio"
    )

    val artistName = listOf(
        "Arijit Singh",
        "KK",
        "Diljit Dosanjh",
        "Kumar Sanu",
        "Kishore Kumar",
        "Shreya Ghosal",
        "Alka Yagnik",
        "Sidhu Moose Wala ",
        "Yo Yo Honey Singh"
    )

    val recentSong = listOf(
        "Song 1",
        "Song 2",
        "Song 3",
        "Song 4",
        "Song 5",
        "Song 6",
        "Song 7"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {

        item {
            CustomHeader(
                headerType = HeaderType.Home,
                onarrowClick = {

                }
            )
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(gridItems) {
                    CustomSongBar(songText = it , onclick = { onclickCustombar(it) })
                }
            }
        }


        item {
            CustomTextView(
                "Picked For you"
            )
        }

        item {
            OnSpotSong()
        }

        item {
            CustomTextView(
                "Popular Radio"
            )
        }

        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(artistName ){name ->
                    MediumSongTile(artistName = name ,onclick = { onclickCustombar(name) })
                }
            }
        }
        item {
            CustomTextView(
                "Recents Song",
            )
        }

        item {
            LazyRow(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(recentSong){songs ->
                    SmallSongTile(songs, "www.google.com")
                }
            }
        }
    }
}
