package com.example.movieproject.presentation.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieproject.R
import com.example.movieproject.domain.usecase.DateFormatUseCase
import com.example.movieproject.domain.usecase.RoundToDecimalUseCase
import com.example.movieproject.presentation.detail.DetailIntent
import com.example.movieproject.presentation.detail.DetailViewModel
import com.example.movieproject.presentation.ui.composables.RatingBar
import com.example.movieproject.utils.Constants
import com.example.movieproject.utils.Constants.POSTER_BASE_ORG_URL
import com.example.movieproject.utils.UiState
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel,
    systemUiController: SystemUiController
) {
    systemUiController.isSystemBarsVisible = false

    val state by viewModel.state.collectAsStateWithLifecycle()

    val brush = Brush.verticalGradient(colors = listOf(Color(0xFF273343), Color(0xFF161E29)))
    val brush2 = Brush.verticalGradient(colors = listOf(Color(29, 39, 51, 0), Color(32, 40, 53, 255)))

    when (state.movieDetailsState) {
        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize().background(brush), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFE82251))
            }
            return
        }
        is UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize().background(brush), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = (state.movieDetailsState as UiState.Error).message, color = Color.White)
                    Text(
                        text = "Retry",
                        color = Color(0xFFE82251),
                        modifier = Modifier.padding(top = 8.dp).clickable { viewModel.onIntent(DetailIntent.Retry) }
                    )
                }
            }
            return
        }
        is UiState.Success -> Unit
    }

    val movieDetail = (state.movieDetailsState as UiState.Success).data
    val movieCast = (state.castState as? UiState.Success)?.data ?: emptyList()

    Column(
        modifier = Modifier.fillMaxSize().background(brush),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(POSTER_BASE_ORG_URL + movieDetail.posterPath)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.fillMaxSize().background(brush2),
                contentAlignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable { navController.popBackStack() }
                            .padding(6.dp)
                    )
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    movieDetail.title?.let {
                        Text(
                            text = it,
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
            movieDetail.voteAverage?.let { avg ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val rating = RoundToDecimalUseCase(avg / 2).invoke()
                    RatingBar(rating = rating)
                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = "  $rating/5  (${movieDetail.voteCount})",
                        color = Color.White
                    )
                }
            }

            movieDetail.genres?.let { genres ->
                val genreText = genres.take(2).mapNotNull { it?.name }.joinToString(", ")
                val langText = if (movieDetail.originalLanguage == "en") "English"
                    else movieDetail.spokenLanguages?.firstOrNull()?.englishName ?: ""
                val runtimeText = movieDetail.runtime?.let { "${it / 60}h ${it % 60}min" } ?: ""
                val dateText = movieDetail.releaseDate?.let { DateFormatUseCase(it).invoke() } ?: ""
                Text(
                    text = listOf(genreText, langText, runtimeText, dateText)
                        .filter { it.isNotBlank() }
                        .joinToString(" | "),
                    color = Color.White
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                horizontalArrangement = Arrangement.Start
            ) {
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
                text = movieDetail.overview ?: "",
                color = Color(0x99FFFFFF),
                maxLines = 5
            )

            LazyRow(
                modifier = Modifier.padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(movieCast) { cast ->
                    Box(
                        modifier = Modifier.width(100.dp).height(100.dp).clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        if (cast?.profilePath != null) {
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(Constants.POSTER_BASE_URL + cast.profilePath)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        } else {
                            Text(
                                text = cast?.name ?: "No Image",
                                textAlign = TextAlign.Center,
                                maxLines = 2,
                                color = Color(0x99FFFFFF)
                            )
                        }
                    }
                }
            }
        }
    }
}
