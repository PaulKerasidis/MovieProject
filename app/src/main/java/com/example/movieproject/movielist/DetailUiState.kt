package com.example.movieproject.movielist

import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.cast.Cast
import com.example.movieproject.utils.UiState

data class DetailUiState(
    val movieDetailsState: UiState<MovieDetails> = UiState.Loading,
    val castState: UiState<List<Cast?>> = UiState.Loading,
)
