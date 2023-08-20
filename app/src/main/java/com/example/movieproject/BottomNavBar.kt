package com.example.movieproject

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


data class BottomNavItem(
    val title: String,
    @DrawableRes val icon: Int,
)

@Composable
fun BottomNavBar() {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val items = listOf(
        BottomNavItem(
            "Home",
            R.drawable.home_red,

        ),
        BottomNavItem(
            "Tickets",
            R.drawable.tickets_red,
        ),
        BottomNavItem(
            "Profile",
            R.drawable.profile_red,
        )
    )


    NavigationBar(
        modifier = Modifier.height(72.dp),
        containerColor = Color(39, 51, 67),

    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { selectedItemIndex = index },
                label = {
                        Text(
                            text = item.title,
                            color = if(selectedItemIndex == index) Color(0xFFE82251) else Color(0x66FFFFFF)
                            )
                },
                icon = {
                   Icon(
                       painter = painterResource(id = item.icon),
                       contentDescription = item.title,
                       tint = if(selectedItemIndex == index) Color(0xFFE82251) else Color(0x66FFFFFF)
                   )
                }
            )
        }
    }

}
