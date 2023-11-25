package com.example.movieproject.data.repository

import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.data.network.response.TrendingMoviesList
import com.example.movieproject.data.network.response.cast.CastList
import com.example.movieproject.utils.Resource

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Resource<PopularMovies>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails>
    suspend fun getTrendingMovies(page: Int): Resource<TrendingMoviesList>
    suspend fun getMovieCast(movieId: Int): Resource<CastList>
}