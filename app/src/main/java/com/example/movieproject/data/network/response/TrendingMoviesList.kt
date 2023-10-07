package com.example.movieproject.data.network.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingMoviesList(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<TrendingMovie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)