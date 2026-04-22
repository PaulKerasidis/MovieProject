package com.example.movieproject.presentation.home

sealed class HomeIntent {
    object LoadMoreMovies : HomeIntent()
    object LoadMoreTrending : HomeIntent()
    object ToggleSearch : HomeIntent()
    data class SearchMovies(val query: String) : HomeIntent()
    data class SelectGenre(val index: Int) : HomeIntent()
}
