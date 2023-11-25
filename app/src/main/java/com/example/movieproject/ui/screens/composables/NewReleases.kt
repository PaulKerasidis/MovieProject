package com.example.movieproject.ui.screens.composables

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.movieproject.ui.screens.navigation.MovieAppScreen
import com.example.movieproject.utils.Constants.POSTER_BASE_URL
import kotlinx.coroutines.launch

@Composable
fun NewReleases(
    navController: NavController,
    movieListViewModel: MovieListViewModel
)
{


    val popularMovies by remember {
        mutableStateOf(movieListViewModel.popularMovies)
    }

    Row(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(28.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "Popular Movies",
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
            text = "Show more",
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
                .clickable { if (popularMovies.value.size - 1 <50) movieListViewModel.loadPopularMovies() else null }
        )
    }

    LazyRow(
        modifier = Modifier.padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(popularMovies.value)
        { movie ->
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(278.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(MovieAppScreen.DetailScreen.route)
                        movieListViewModel.loadMovieDetails(movie.id!!)
                        movieListViewModel.loadMovieCast(movie.id!!)

                    }

            ) {
                Box(modifier = Modifier.fillMaxSize())
                {

                     AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(POSTER_BASE_URL + movie.posterPath)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}


