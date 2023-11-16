package com.example.movieproject.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieproject.movielist.MovieListViewModel
import com.example.movieproject.ui.screens.DetailScreen

import com.example.movieproject.ui.screens.HomeScreen


@Composable
fun MovieApp() {

    val navController = rememberNavController()
    val movieListViewModel: MovieListViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = MovieAppScreen.HomeScreen.route
    ) {
        composable(route = MovieAppScreen.HomeScreen.route) {
            HomeScreen(navController = navController, movieListViewModel = movieListViewModel)
        }
        composable(route = MovieAppScreen.DetailScreen.route)
        {
            DetailScreen(
                navController = navController,
                movieListViewModel = movieListViewModel
            )
        }
    }

}