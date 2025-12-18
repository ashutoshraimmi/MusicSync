package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderButton(
    buttonText: String,
    onClick: () -> Unit,
    enabled: Boolean ,
    modifier: Modifier,
    backgroundColor: Color
) {
    Box(contentAlignment = Alignment.Center , modifier = Modifier.padding(4.dp, 0.dp , 0.dp , 0.dp ) ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor
            )
        ) {
            Text(
                text = buttonText,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
