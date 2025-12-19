package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MediumSongTile(artistName : String, onclick : () -> Unit) {
    Box(modifier = Modifier
        .height(150.dp)
        .width(150.dp)
        .clickable{onclick()}
        .background(color = Color.Gray, shape = RoundedCornerShape(16.dp))
    ){
        Column(modifier = Modifier.fillMaxHeight().padding(16.dp, 4.dp)){

            Row(
                modifier = Modifier
                    .padding(4.dp, 8.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.star_off),
                    "RowImage1",
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth(),

                    )
                Image(
                    painter = painterResource(R.drawable.star_off),
                    "RowImage1",
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth(0.25f)
                )
                Image(
                    painter = painterResource(R.drawable.star_off),
                    "RowImage1",
                    modifier = Modifier
                        .fillMaxHeight(1.0f)
                        .fillMaxWidth(0.25f)
                )
            }
            Text(
                text = artistName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,   // ✅ correct
                color = Color.Black,
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(),
                lineHeight = 20.sp                // ✅ correct
            )
        }
    }
}