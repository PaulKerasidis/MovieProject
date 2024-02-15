package com.example.movieproject.presentation.ui.navigation

sealed class MovieAppScreen(val route:String) {
    object HomeScreen : MovieAppScreen("home_screen")
    object DetailScreen : MovieAppScreen("detail_screen")


}
