package com.ashutosh.musicsync.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CustomSmallMusicPlayer(imageUrl: String, songTitle : String, artistName : String, onPlayPause : ()-> Unit, isPlaying: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().height(64.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
        ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxHeight().width(64.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxWidth(0.6f).fillMaxHeight(),
            Arrangement.Center
            ) {
            Text(
                text = songTitle,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = artistName,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 8.sp
            )
        }
        IconButton(onClick = onPlayPause) {
            Icon(
                imageVector = if (isPlaying)
                    Icons.Default.PlayArrow
                else
                    Icons.Default.PlayArrow,
                contentDescription = "Play Pause",
                tint = Color.White,
                modifier = Modifier.size(72.dp)
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_MASK)
@Composable
fun previewCustomPlayTile(){
    CustomSmallMusicPlayer("http.com", "AShutosh", "Rai"  , {} ,true)
}