package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.R

@Composable
fun MediumSongTile(artistName : String, onclick : () -> Unit) {
    Box(
        modifier = Modifier
            .height(160.dp)
            .width(160.dp)
            .shadow(4.dp, shape = RoundedCornerShape(16.dp))
            .clickable{onclick()}
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp)
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(12.dp)
        ){
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.star_off),
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth(),
                )
                Image(
                    painter = painterResource(R.drawable.star_off),
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth(0.25f)
                )
                Image(
                    painter = painterResource(R.drawable.star_off),
                    contentDescription = "Album Art",
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth(0.25f)
                )
            }
            Text(
                text = artistName,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(),
                maxLines = 1
            )
        }
    }
}