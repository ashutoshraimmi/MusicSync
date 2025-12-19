package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomSongBar(
    songText: String,
    modifier: Modifier = Modifier   ,
    onclick : ()->Unit

) {
    val displayText =
        if (songText.length > 15) songText.take(15) + "..."
        else songText

    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth()
            .height(48.dp)   // ✅ default height
            .background(color = Color.Gray, shape = shape)
            .border(1.dp, color = Color.Black, shape = shape)
            .clickable{onclick()},
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Face,
                contentDescription = "Card Image",
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White, CircleShape)
                    .padding(2.dp)
            )

            Box(modifier = Modifier.padding(start = 8.dp)) {
                Text(displayText, fontSize = 12.sp)
            }
        }
    }
}

@Preview
@Composable
fun previewCustomSongBar(){
    CustomSongBar("ashutosh" , onclick = {})
}