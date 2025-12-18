package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MusicListTile() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(8.dp, 8.dp),
            Arrangement.Center


        ) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.9f)) {
                Text("Heading", fontSize = 20.sp)
                Text("SubHeading", fontSize = 12.sp)
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Options",
            )


        }
    }
}

@Preview
@Composable
fun previewMusicListTile() {
    MusicListTile()
}