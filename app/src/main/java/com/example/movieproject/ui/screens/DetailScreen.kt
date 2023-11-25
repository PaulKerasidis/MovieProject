package com.example.movieproject.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieproject.R
import com.example.movieproject.movielist.MovieListViewModel
import com.example.movieproject.ui.screens.composables.RatingBar
import com.example.movieproject.ui.screens.navigation.MovieAppScreen
import com.example.movieproject.utils.Constants
import com.example.movieproject.utils.Constants.POSTER_BASE_ORG_URL
import com.google.accompanist.systemuicontroller.SystemUiController


@Composable
fun DetailScreen(
    navController: NavController,
    movieListViewModel: MovieListViewModel,
    systemUiController: SystemUiController
) {
    systemUiController.isSystemBarsVisible = false

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF273343), Color(0xFF161E29)),
    )
    val brush2 = Brush.verticalGradient(
        colors = listOf(
            Color(29, 39, 51, 0),
            Color(32, 40, 53, 255)
        ),
    )

    val movieDetail by remember {
        mutableStateOf(movieListViewModel.movieDetails)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)

        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(POSTER_BASE_ORG_URL + movieDetail.value.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush2),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.CenterStart
                ){
                   Icon(
                       Icons.Default.ArrowBackIosNew,
                       contentDescription = null,
                       tint = Color.White,
                       modifier = Modifier
                           .padding(start =16.dp)
                           .clickable {
                               navController.navigate(MovieAppScreen.HomeScreen.route)
                           }
                           .padding(6.dp)
                   )
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {


                    if (movieDetail.value.title != null) {
                        Text(
                            text = movieDetail.value.title!!,
                            style = TextStyle(
                                fontSize = 40.sp,
                                fontFamily = FontFamily(Font(R.font.poppinsbold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFFF3F3F4),
                                textAlign = TextAlign.Center,
                            ),
                            maxLines = 1
                        )
                    }

                }

            }
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (movieDetail.value.voteAverage != null) {
                    val rating = roundToDecimal(movieDetail.value.voteAverage!! / 2)
                    RatingBar(rating = (rating))

                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = "  ${rating}/5  (${movieDetail.value.voteCount})",
                        color = Color.White
                    )
                }
            }
            if (movieDetail.value.genres != null) {
                if (movieDetail.value.genres!![1] != null) {
                    Text(
                        text = "${movieDetail.value.genres!![0]?.name ?: " "}, ${movieDetail.value.genres!![1]?.name ?: " "}" +
                                " | ${
                                    if (movieDetail.value.originalLanguage == "en") "English" else movieDetail.value.spokenLanguages?.get(
                                        0
                                    )!!.englishName
                                }" +
                                " | ${movieDetail.value.runtime?.div(60)}h ${
                                    movieDetail.value.runtime?.rem(
                                        60
                                    )
                                }min" +
                                " | ${
                                    if (movieDetail.value.releaseDate != null) dateFormat(
                                        movieDetail.value.releaseDate!!
                                    ) else " "
                                }",
                        color = Color.White
                    )
                    Text(
                        text = "",
                        color = Color.White
                    )
                } else if (movieDetail.value.genres!![0] != null) {
                    Text(
                        text = "${movieDetail.value.genres!![0]?.name ?: " "}" +
                                " | ${
                                    if (movieDetail.value.originalLanguage == "en") "English" else movieDetail.value.spokenLanguages?.get(
                                        0
                                    )!!.englishName
                                }" +
                                " | ${movieDetail.value.runtime?.div(60)}h ${
                                    movieDetail.value.runtime?.rem(
                                        60
                                    )
                                }min" +
                                " | ${
                                    if (movieDetail.value.releaseDate != null) dateFormat(
                                        movieDetail.value.releaseDate!!
                                    ) else " "
                                }",
                        color = Color.White
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                horizontalArrangement = Arrangement.Start
            )
            {
                Text(
                    text = "Summary",
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontFamily = FontFamily(Font(R.font.poppinsbold)),
                        color = Color(0xFFF3F3F4),

                        )
                )
            }
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = movieDetail.value.overview?: "",
                    color = Color(0x99FFFFFF),
                    maxLines = 5
                )
            LazyRow(
                modifier = Modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            )
            {
               items(movieListViewModel.movieCast.value){cast->
                   Box(
                       modifier = Modifier
                           .width(100.dp)
                           .height(100.dp)
                           .clip(CircleShape),
                       contentAlignment = Alignment.Center
                   ){
                       if (cast?.profilePath != null) {
                           AsyncImage(
                               modifier = Modifier.fillMaxSize(),
                               model = ImageRequest.Builder(LocalContext.current)
                                   .data(Constants.POSTER_BASE_URL + cast!!.profilePath)
                                   .crossfade(true)
                                   .build(),
                               contentDescription = null,
                               contentScale = ContentScale.FillWidth
                           )
                       }else {
                               Text(
                                   text = "No Image",
                                   textAlign = TextAlign.End,
                                   color = Color(0x99FFFFFF)
                               )
                       }
                   }
               }
            }
        }
    }
}

fun roundToDecimal(doubleValue: Double, decimalPlaces: Int = 1): Double {
    val multiplier = Math.pow(10.0, decimalPlaces.toDouble())
    val roundedValue = Math.round(doubleValue * multiplier) / multiplier
    return roundedValue
}

fun dateFormat(date: String): String {
    val day = date.substring(8, 10)
    val month = month(date.substring(5, 7))
    val year = date.substring(0, 4)
    return "$day ${month} $year"

}

fun month(month: String): String {
    return when (month) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "May"
        "06" -> "Jun"
        "07" -> "Jul"
        "08" -> "Aug"
        "09" -> "Sep"
        "10" -> "Oct"
        "11" -> "Nov"
        else -> "Dec"

    }
}


