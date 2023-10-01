package com.example.movieproject.data.repository

import com.example.movieproject.data.network.api.MovieApi
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MovieRepository @Inject constructor(
    private val api: MovieApi
) {

    suspend fun getPopularMovies(page: Int,apiKey : String): Resource<PopularMovies>{
        var response = try {
            api.getPopularMovies(page,apiKey)
        }catch (e: Exception){
            return Resource.Error("An unknown error occured")
        }
        return Resource.Success(response)
    }
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails>{
        var response = try {
            api.getMovieDetails(movieId)
        }catch (e: Exception){
            return Resource.Error("An unknown error occured")
        }
        return Resource.Success(response)
    }

}