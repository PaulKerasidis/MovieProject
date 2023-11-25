package com.example.movieproject.ui.screens.composables

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieproject.movielist.MovieListViewModel
import com.example.movieproject.ui.screens.navigation.MovieAppScreen
import com.example.movieproject.utils.Constants.POSTER_BASE_URL

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider(
    movieListViewModel: MovieListViewModel,
    navController: NavController,
) {

    val trendingMovies by remember {
        mutableStateOf(movieListViewModel.trendingMovies)
    }

    val pagerState = rememberPagerState(initialPage = 0)

    HorizontalPager(
        pageCount = trendingMovies.value.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 30.dp),
        modifier = Modifier
            .padding(top = 24.dp, bottom = 22.dp)
            .wrapContentSize(),
        pageSpacing = -27.5.dp

    ) { index ->
        val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
        val imageSize by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.75f else 1f
        )
        Log.d("TAG", "${pageOffset} ${imageSize}")
        Log.i("TAG", "${pagerState.currentPageOffsetFraction}")

        Card(
            modifier = Modifier
                .width(350.dp)
                .height(196.dp)
                .graphicsLayer {
                    scaleX = imageSize
                    scaleY = imageSize
                }
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    navController.navigate(MovieAppScreen.DetailScreen.route)
                    movieListViewModel.loadMovieDetails(trendingMovies.value[index].id!!)
                    movieListViewModel.loadMovieCast(trendingMovies.value[index].id!!)
                }
        )
        {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            )
            {
                if (((index < trendingMovies.value.size-1) or (index == 59)) and (index <= 59)){
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(POSTER_BASE_URL + trendingMovies.value[index].backdropPath)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie",
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .background(color = Color.Black.copy(alpha = 0.5f)),
                )
                {
                    trendingMovies.value[index].title?.let {
                        Text(
                            text = it,
                            fontSize = 20.sp
                        )
                    }
                }
                }else if((index == trendingMovies.value.size - 1) and (index <= 39)){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { movieListViewModel.loadTrendingMovies() },
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                            )
                    }
                }
            }
        }


    }


}

