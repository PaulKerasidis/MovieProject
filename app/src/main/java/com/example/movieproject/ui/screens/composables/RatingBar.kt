package com.example.movieproject.ui.screens.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(
    rating: Double = 0.0,
    stars : Int = 5,
    starsColor: Color = Color.Yellow
) {
    Log.i("Doble", "$rating")

    var isHalfStar = (rating % 1) >= 0.5
    Row{
        for ( index in 1.. stars){
            Icon(
                contentDescription = null,
                tint = starsColor,
                imageVector = if (index <= rating){
                    Icons.Rounded.Star
                }else{
                    if (isHalfStar){
                        isHalfStar = false
                        Icons.Rounded.StarHalf
                    }else{
                        Icons.Rounded.StarOutline
                    }

                }
            )
        }
    }

}