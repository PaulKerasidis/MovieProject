package com.example.movieproject.movielist

import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.TrendingMovie
import com.example.movieproject.utils.UiState

data class HomeUiState(
    val moviesState: UiState<List<Movies>> = UiState.Loading,
    val trendingState: UiState<List<TrendingMovie>> = UiState.Loading,
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val selectedGenreIndex: Int = 0,
    val isPaginating: Boolean = false,
)
