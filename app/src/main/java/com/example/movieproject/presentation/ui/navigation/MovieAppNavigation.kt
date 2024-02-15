package com.example.movieproject.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieproject.movielist.MovieListViewModel
import com.example.movieproject.presentation.ui.screens.DetailScreen
import com.example.movieproject.presentation.ui.screens.HomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun MovieApp() {

    val navController = rememberNavController()
    val movieListViewModel: MovieListViewModel = hiltViewModel()
    val systemUiController = rememberSystemUiController()

    NavHost(
        navController = navController,
        startDestination = MovieAppScreen.HomeScreen.route
    ) {
        composable(route = MovieAppScreen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                movieListViewModel = movieListViewModel,
                systemUiController = systemUiController
                )
        }
        composable(route = MovieAppScreen.DetailScreen.route)
        {
            DetailScreen(
                navController = navController,
                movieListViewModel = movieListViewModel,
                systemUiController = systemUiController
            )
        }
    }

}