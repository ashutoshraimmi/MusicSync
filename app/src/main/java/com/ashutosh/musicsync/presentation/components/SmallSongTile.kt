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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun SmallSongTile(songName: String, imageUrl: String) {
    val roundedShape = RoundedCornerShape(12.dp)
    Box(
        modifier = Modifier
            .width(130.dp)
            .height(140.dp)
            .shadow(3.dp, shape = roundedShape)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Album Tile",
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(roundedShape)
                    .background(color = MaterialTheme.colorScheme.surfaceVariant)
            )
            Text(
                text = songName,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
    }
}

@Composable
@Preview("SmallSongTile")
fun PreviewSmallTile() {
    SmallSongTile("kjhsdkajhsj", "http//skdadsl.com")

}
