package com.example.movieproject

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewReleases() {
    val releases = listOf(
        R.drawable.images1,
        R.drawable.images2,
        R.drawable.images3,
    )
    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){

            Text(
                text = "New Releases",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFF3F3F4),
                ),
                modifier = Modifier
                    .padding(start = 16.dp)
            )
        Text(
            text = "View All",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.poppinsregular)),
                fontWeight = FontWeight(600),
                color = Color(0xFFE82251),
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
            ),
            modifier = Modifier
                    .padding(end = 16.dp)
        )
    }

    LazyRow(
        modifier = Modifier.
        padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        items(releases)
        {
            Image(
                modifier = Modifier
                    .width(200.dp)
                    .height(278.dp)
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = it),
                contentDescription = "Movie Image",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}