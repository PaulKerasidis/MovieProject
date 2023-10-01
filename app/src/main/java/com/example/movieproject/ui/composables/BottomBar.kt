package com.example.movieproject.ui.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieproject.R

data class BottomBarItem(
    val title: String,
    @DrawableRes val icon: Int,
)

@Composable
fun BottomBar() {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val items = listOf(
        BottomBarItem(
            "Home",
            R.drawable.home,

            ),
        BottomBarItem(
            "Tickets",
            R.drawable.tickets,
        ),
        BottomBarItem(
            "Profile",
            R.drawable.profile,
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = Color(39, 51, 67)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->

            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(90.dp)
                    .clickable {selectedItemIndex = index},
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "Home Screen",
                        tint = if(selectedItemIndex == index) Color(0xFFE82251) else Color(0x66FFFFFF)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = item.title,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppinsregular)),
                            fontWeight = FontWeight(600),
                            color = if(selectedItemIndex == index) Color(0xFFE82251) else Color(0x66FFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }

}