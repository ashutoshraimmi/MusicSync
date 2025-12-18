package com.ashutosh.musicsync.presentation.components

import android.R
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter

@Composable
fun OnSpotSong() {
    Box(modifier = Modifier.fillMaxWidth(1.0f).height(132.dp).background(color = Color.Gray , shape = RoundedCornerShape(24.dp)).padding(8.dp, 4.dp)){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(R.drawable.star_off),
                contentDescription = "This is song tile image",
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.30f),
                contentScale = ContentScale.Crop
            )
            CustomTextView("Song Picked for You ")
        }
    }
}

@Preview
@Composable
fun previewOnSpotSong(){
    OnSpotSong()
}