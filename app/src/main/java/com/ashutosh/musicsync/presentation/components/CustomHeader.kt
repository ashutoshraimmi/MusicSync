package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomHeader(headerType: HeaderType, onarrowClick : ()-> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 12.dp)
    ) {
        when (headerType) {
            is HeaderType.Home -> HomeTopBar()

            is HeaderType.Search -> SearchTopBar(
                text = headerType.text,
                onarrowClick={ onarrowClick() }
            )

            is HeaderType.SongList -> CommonHeaderBar(
                onarrowClick = {
                    onarrowClick()
                }
            )

            is HeaderType.Profile -> SearchTopBar(
                text = headerType.text,
                onarrowClick = {onarrowClick()}
            )
        }
    }
}
@Composable
fun HomeTopBar(){
    LazyRow(
        modifier = Modifier.height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        item{
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .shadow(2.dp, shape = CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item{
            HeaderButton("All" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = MaterialTheme.colorScheme.surfaceVariant)
        }
        item {
            HeaderButton("Wrapped" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = MaterialTheme.colorScheme.surfaceVariant)
        }
        item {
            HeaderButton("Music" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = MaterialTheme.colorScheme.surfaceVariant)
        }
        item {
            HeaderButton("Podcasts" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}
@Composable
fun SearchTopBar(text : String, onarrowClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .shadow(2.dp, shape = CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = CircleShape
                    )
                    .clickable { },
                contentAlignment = Alignment.Center,
            )
            {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun CommonHeaderBar(onarrowClick: ()-> Unit) {
    Box(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .shadow(2.dp, shape = CircleShape)
                .clickable { onarrowClick() }
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center,
        )
        {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
sealed class HeaderType {
    object Home : HeaderType()
    data class Search(val text: String) : HeaderType()
    object SongList : HeaderType()
    data class Profile(val text: String) : HeaderType()
}
