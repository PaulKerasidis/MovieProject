package com.example.movieproject.ui.screens.navigation

sealed class MovieAppScreen(val route:String) {
    object HomeScreen : MovieAppScreen("home_screen")
    object DetailScreen : MovieAppScreen("detail_screen")


}
