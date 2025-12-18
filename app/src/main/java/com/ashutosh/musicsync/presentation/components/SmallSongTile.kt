package com.ashutosh.musicsync.presentation.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun SmallSongTile(songName: String, imageUrl: String) {
    val topRoundedShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    )
    Box(modifier = Modifier
        .width(120.dp)
        .height(120.dp)) {
        Column(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Album Tile",
                modifier = Modifier
                    .height(90.dp)
                    .clip(topRoundedShape)
                    .background(color = Color.Gray)
            )
            Text(songName, fontSize = 12.sp, modifier = Modifier
                .height(30.dp)
                .background(Color.DarkGray)
                .fillMaxWidth().padding(0.dp, 6.dp),

                textAlign = TextAlign.Center,

                

            )

        }
    }

}

@Composable
@Preview("SmallSongTile")
fun PreviewSmallTile() {
    SmallSongTile("kjhsdkajhsj", "http//skdadsl.com")

}
