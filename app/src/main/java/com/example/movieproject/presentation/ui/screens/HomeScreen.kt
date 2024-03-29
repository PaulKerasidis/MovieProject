package com.example.movieproject.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.movieproject.movielist.MovieListViewModel
import com.example.movieproject.presentation.ui.composables.BottomBar
import com.example.movieproject.presentation.ui.composables.Genres
import com.example.movieproject.presentation.ui.composables.ImageSlider
import com.example.movieproject.presentation.ui.composables.NewReleases
import com.example.movieproject.presentation.ui.composables.TopBar
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    movieListViewModel: MovieListViewModel,
    systemUiController: SystemUiController
) {
    systemUiController.isSystemBarsVisible =true
    systemUiController.setSystemBarsColor(
        color = Color(39, 51, 67)
    )

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF273343), Color(0xFF161E29)),
    )

    Scaffold(

        bottomBar = { BottomBar() }

    ) {
        it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)

        ) {

            TopBar()

            ImageSlider(movieListViewModel = movieListViewModel, navController = navController)

            Genres()

            NewReleases(navController = navController, movieListViewModel = movieListViewModel)

        }
    }
}