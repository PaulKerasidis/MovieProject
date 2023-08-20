package com.example.movieproject

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Genres() {

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }

    val genres = listOf<String>(
        "Drama","Action","Romance", "Comedy","Heroes"
    )
   Column(

   ) {


       Box(
           modifier = Modifier
               .padding(start = 16.dp, bottom = 8.dp)
               .width(68.dp)
               .height(21.dp),
           contentAlignment = Alignment.CenterStart

       ) {

           Text(
               text = "Genres",
               style = TextStyle(
                   fontSize = 14.sp,
                   fontFamily = FontFamily(Font(R.font.poppinsregular)),
                   fontWeight = FontWeight(500),
                   color = Color(0xFFF3F3F4),
               )
           )
       }
       LazyRow(
        modifier = Modifier.
           padding(start = 16.dp),
           horizontalArrangement = Arrangement.spacedBy(10.dp)


       ){
        itemsIndexed(genres){index,it->

            Box(
                modifier = Modifier
                    .width(75.dp)
                    .height(30.dp)
                    .background(color = if(selectedItem == index) {
                        Color(0xFFE82251)
                    }else{
                        Color.Transparent
                         }
                        , shape = RoundedCornerShape(size = 28.dp))
                    .border(width = if(selectedItem != index) 1.dp else 0.dp
                        , color = Color(0x29FFFFFF), shape = RoundedCornerShape(size = 28.dp))
                    .clickable {
                               selectedItem = index
                    },
                contentAlignment = Alignment.Center


                ) {
                Text(
                    text = it,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppinsregular)),
                        fontWeight = FontWeight(400),
                        color = if(selectedItem == index) Color(0xFFFFFFFF) else Color(0x66FFFFFF),
                    ),
                )
            }
        }
       }
   }

}

@Preview
@Composable
fun GenresPreview() {
    Genres()
}