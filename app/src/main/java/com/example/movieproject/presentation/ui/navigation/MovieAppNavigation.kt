package com.example.movieproject.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieproject.presentation.detail.DetailViewModel
import com.example.movieproject.presentation.home.HomeViewModel
import com.example.movieproject.presentation.ui.screens.DetailScreen
import com.example.movieproject.presentation.ui.screens.HomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()

    NavHost(
        navController = navController,
        startDestination = MovieAppScreen.HomeScreen.route
    ) {
        composable(route = MovieAppScreen.HomeScreen.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                navController = navController,
                viewModel = homeViewModel,
                systemUiController = systemUiController
            )
        }
        composable(
            route = MovieAppScreen.DetailScreen.route,
            arguments = listOf(MovieAppScreen.DetailScreen.argument)
        ) {
            val detailViewModel: DetailViewModel = hiltViewModel()
            DetailScreen(
                navController = navController,
                viewModel = detailViewModel,
                systemUiController = systemUiController
            )
        }
    }
}
