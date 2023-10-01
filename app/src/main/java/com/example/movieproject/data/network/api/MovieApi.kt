package com.example.movieproject.data.network.api

import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("/movie/popular")
    suspend fun getPopularMovies(
        @Query("page")page : Int,
        @Query("api_key")apiKey : String = API_KEY
    ): PopularMovies


    @GET("/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id")movieId: Int): MovieDetails

}