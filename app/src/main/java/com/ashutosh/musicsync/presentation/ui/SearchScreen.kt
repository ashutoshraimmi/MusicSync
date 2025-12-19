package com.example.saavn.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ashutosh.musicsync.domain.model.Song
import com.ashutosh.musicsync.presentation.commonui.AppNavItem
import com.ashutosh.musicsync.presentation.components.*
import com.ashutosh.musicsync.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val songs by viewModel.songs.collectAsState()
    var query by remember { mutableStateOf("") }

    val musicStyle = remember {
        listOf("#hindi hip hop", "#urdu hip hop", "#clean girl")
    }

    val browseAllList = remember {
        listOf(
            "Made For You", "New Releases", "Trending", "Top Charts",
            "Podcasts", "Live Events", "Hindi", "Punjabi", "Tamil",
            "Telugu", "Malayalam", "Marathi", "Bengali", "Gujarati",
            "Kannada", "English", "Romance", "Party", "Workout",
            "Chill", "Focus", "Sleep", "Mood", "Travel", "Devotional",
            "Indie", "Hip-Hop", "Pop", "Rock", "Jazz", "Classical"
        )
    }
    val gridItem = listOf(
        "Music",
        "Podcasts",
        "Live Events",
        "Home of I-Pop"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        // 🔹 Header
        item {
            CustomHeader(headerType = HeaderType.Search("Search"), onarrowClick = {

            })
            Spacer(Modifier.height(12.dp))
        }

        // 🔹 Search bar
        item {
            CustomSearchBar(
                query = query,
                onInputSearch = { newQuery ->
                    query = newQuery
                    viewModel.onQueryChanged(newQuery)
                }
            )
            Spacer(Modifier.height(16.dp))
        }


        // 🔹 Search results
        if (songs.isNotEmpty()) {
            items(songs) { song ->
                SongRow(song = song, onClick = { })
            }
        }
        item {
            Spacer(Modifier.height(16.dp))
            TopCategoryGrid(gridItem)
        }


        // 🔹 Start browsing section
        item {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Start Browsing",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(12.dp))
        }


        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(musicStyle) { tag ->
                    MediumSongTile( artistName = tag , onclick = {

                    })
                }
            }
            Spacer(Modifier.height(20.dp))
        }

        item {
            Text(
                text = "Browse All",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(12.dp))
        }

        item {
            BrowseAllStaticGrid(browseAllList)
        }

        item {
            Spacer(Modifier.height(32.dp))
        }
    }
}
@Composable
fun TopCategoryGrid(items: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    CustomSongBar(
                        songText = item,
                        modifier = Modifier.weight(1f),
                        onclick = {
                        }
                    )
                }

                // Fill space if odd item count
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun BrowseAllStaticGrid(items: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    CustomSongBar(
                        songText = item,
                        modifier = Modifier.weight(1f),
                        onclick = {

                        }
                    )
                }

                // Fill empty space if odd item
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
@Composable
fun SongRow(song: Song, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = song.image,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(song.title, maxLines = 1)
            Text(song.artist ?: "", style = MaterialTheme.typography.bodySmall)
        }
    }
}
