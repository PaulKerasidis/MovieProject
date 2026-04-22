package com.example.movieproject.presentation.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class MovieAppScreen(val route: String) {
    object HomeScreen : MovieAppScreen("home_screen")
    object DetailScreen : MovieAppScreen("detail_screen/{movieId}") {
        fun createRoute(movieId: Int) = "detail_screen/$movieId"
        val argument = navArgument("movieId") { type = NavType.IntType }
    }
}
