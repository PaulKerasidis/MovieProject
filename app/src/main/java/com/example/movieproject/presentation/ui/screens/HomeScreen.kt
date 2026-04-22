package com.example.movieproject.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.movieproject.movielist.MovieIntent
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
    systemUiController.isSystemBarsVisible = true
    systemUiController.setSystemBarsColor(color = Color(39, 51, 67))

    val state by movieListViewModel.homeState.collectAsStateWithLifecycle()

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF273343), Color(0xFF161E29)),
    )

    Scaffold(bottomBar = { BottomBar() }) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ) {
            TopBar(
                isSearchActive = state.isSearchActive,
                searchQuery = state.searchQuery,
                onSearchToggle = { movieListViewModel.onIntent(MovieIntent.ToggleSearch) },
                onQueryChange = { movieListViewModel.onIntent(MovieIntent.SearchMovies(it)) }
            )

            ImageSlider(
                movieListViewModel = movieListViewModel,
                navController = navController
            )

            Genres(
                selectedIndex = state.selectedGenreIndex,
                onGenreSelected = { movieListViewModel.onIntent(MovieIntent.SelectGenre(it)) }
            )

            NewReleases(
                navController = navController,
                movieListViewModel = movieListViewModel
            )
        }
    }
}
