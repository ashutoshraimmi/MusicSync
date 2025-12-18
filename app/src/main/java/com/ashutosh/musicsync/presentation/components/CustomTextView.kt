package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextView(text : String){
    Text(
        text,
        fontSize = 22.sp,
        modifier = Modifier.padding(vertical = 16.dp),
        fontWeight = FontWeight.Bold
    )
}