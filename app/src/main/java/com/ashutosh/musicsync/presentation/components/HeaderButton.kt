package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderButton(
    buttonText: String,
    onClick: () -> Unit,
    enabled: Boolean ,
    modifier: Modifier,
    backgroundColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 0.dp)
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
