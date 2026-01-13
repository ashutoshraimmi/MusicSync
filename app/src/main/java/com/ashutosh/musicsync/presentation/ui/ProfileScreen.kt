package com.ashutosh.musicsync.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashutosh.musicsync.presentation.components.CustomHeader
import com.ashutosh.musicsync.presentation.components.HeaderButton
import com.ashutosh.musicsync.presentation.components.HeaderType
import com.ashutosh.musicsync.presentation.components.LibraryTile

@Composable
fun ProfileScreen() {
    val filterType = listOf(
        "Playlists", "Podcasts", "Albums", "Artists"
    )
    val listofLibrary = listOf(
        "Liked Songs",
        "Your Episodes",
        "Trending Now India",
        "New Episodes",
        "Trending Valentines Day",
        "90s Bollywood",
        "Best of Brodha"
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentPadding = PaddingValues(vertical = 4.dp),
    ) {
        item {
            CustomHeader(headerType = HeaderType.Profile("Your Library"), onarrowClick = {

            })
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                items(filterType) { filter ->
                    HeaderButton(
                        buttonText = filter,
                        onClick = {
                            // handle click //
                        },
                        enabled = true,
                        modifier = Modifier,
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Arrow Down",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "Recents",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        items(listofLibrary){item->
                    LibraryTile(item)
        }
    }
}
