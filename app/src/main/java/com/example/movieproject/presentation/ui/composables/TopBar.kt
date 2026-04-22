package com.example.movieproject.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieproject.R

@Composable
fun TopBar(
    isSearchActive: Boolean = false,
    searchQuery: String = "",
    onSearchToggle: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(Color(39, 51, 67))
    ) {
        if (isSearchActive) {
            BasicTextField(
                value = searchQuery,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.poppinsregular)),
                    color = Color.White
                ),
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                decorationBox = { inner ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search movies...",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsregular)),
                                color = Color(0x66FFFFFF)
                            )
                        )
                    }
                    inner()
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 16.dp, bottom = 5.dp, top = 1.dp)
            ) {
                Column {
                    Text(
                        text = "Location",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsregular)),
                            fontWeight = FontWeight(500),
                            color = Color(0x99FFFFFF)
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Chandigarh",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsregular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFFFFFFF)
                            )
                        )
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = painterResource(R.drawable.baseline_arrow_drop_down_24),
                                contentDescription = "Drop Down Arrow",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .height(46.dp)
                .then(if (isSearchActive) Modifier else Modifier.fillMaxWidth()),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "Search",
                    tint = if (isSearchActive) Color(0xFFE82251) else Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { onSearchToggle() }
                )
                if (!isSearchActive) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_notifications_none_24),
                        contentDescription = "Notification",
                        tint = Color.White,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview(

) {
    TopBar()
}