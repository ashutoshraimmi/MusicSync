package com.ashutosh.musicsync.presentation.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomHeader(headerType: HeaderType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 16.dp, 8.dp, 4.dp)
    ) {
        when (headerType) {
            is HeaderType.Home -> HomeTopBar()

            is HeaderType.Search -> SearchTopBar(
                text = headerType.text
            )

            is HeaderType.SongList -> CommonHeaderBar()

            is HeaderType.Profile -> SearchTopBar(
                text = headerType.text
            )
        }
    }
}
@Composable
fun HomeTopBar(){
    LazyRow(modifier = Modifier.height(48.dp) , verticalAlignment = Alignment.CenterVertically) {
        item{
            Box(modifier = Modifier.size(40.dp).background(
                color = Color.DarkGray,
                shape = CircleShape,
            ),
                contentAlignment = Alignment.Center){
                Icon(Icons.Default.Person, contentDescription = "PP")
            }
        }
        item{
            HeaderButton("All" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = Color.Gray)
        }
        item {
            HeaderButton("Wrapped" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = Color.Gray)
        }
        item {
            HeaderButton("Music" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = Color.Gray)
        }
        item {
            HeaderButton("Podcasts" , onClick = {
                // handle click //
            } ,  true,  modifier = Modifier , backgroundColor = Color.Gray)
        }


    }
}
@Composable
fun SearchTopBar(text : String) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row() {
            Box(modifier = Modifier.size(40.dp).background(
                color = Color.DarkGray,
                shape = CircleShape),
                contentAlignment = Alignment.Center,
                )
            {
                Icon(Icons.Default.Person, contentDescription = "PP")
            }
            Box(
                modifier = Modifier.fillMaxHeight().padding(12.dp, 0.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Composable
fun CommonHeaderBar() {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Row() {
            Box(modifier = Modifier.size(40.dp).background(
                color = Color.DarkGray,
                shape = CircleShape),
                contentAlignment = Alignment.Center,
            )
            {
                Icon(Icons.Default.ArrowBack, contentDescription = "PP")
            }

        }
    }
}
sealed class HeaderType {
    object Home : HeaderType()
    data class Search(val text: String) : HeaderType()
    object SongList : HeaderType()
    data class Profile(val text: String) : HeaderType()
}
