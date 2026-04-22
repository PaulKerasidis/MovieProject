package com.example.movieproject.movielist

sealed class MovieIntent {
    object LoadMoreMovies : MovieIntent()
    object LoadMoreTrending : MovieIntent()
    object ToggleSearch : MovieIntent()
    data class SearchMovies(val query: String) : MovieIntent()
    data class SelectGenre(val index: Int) : MovieIntent()
    data class LoadMovieDetails(val movieId: Int) : MovieIntent()
    data class LoadMovieCast(val movieId: Int) : MovieIntent()
}
