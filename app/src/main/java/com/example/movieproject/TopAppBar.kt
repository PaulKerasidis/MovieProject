package com.example.movieproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun TopAppBar(

) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .background(Color(39, 51, 67))
    )
    {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(start = 16.dp, bottom = 5.dp, top = 1.dp)
                .background(Color(39, 51, 67))
        ) {
            Column(

            ) {

                Text(
                    text = "Location",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppinsregular)),
                        fontWeight = FontWeight(500),
                        color = Color(0x99FFFFFF)
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Chandigarh",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsregular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF)
                        ),
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Icon(
                        painter = painterResource(
                            id = R.drawable.baseline_arrow_drop_down_24
                        ),
                        contentDescription = "Drop Down Arrow",
                        tint = Color.White
                    )

                }
            }

        }



        Box(modifier = Modifier
            .height(46.dp)
            .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        )
        {
            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp)

                )

                Icon(
                    painter = painterResource(R.drawable.baseline_notifications_none_24),
                    contentDescription = "Notification",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp)


                )
            }
        }
    }
}

@Preview
@Composable
fun TopAppBarPreview(

) {
    TopAppBar()
}