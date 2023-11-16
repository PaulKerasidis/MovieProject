package com.example.movieproject.data.domain

import com.example.movieproject.data.network.api.MovieApi
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.data.network.response.TrendingMoviesList
import com.example.movieproject.data.repository.MovieRepository
import com.example.movieproject.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MovieRepositoryImp @Inject constructor(
    private val api: MovieApi
):MovieRepository {

    override suspend fun getPopularMovies(page: Int): Resource<PopularMovies> {
        var response = try {
            api.getPopularMovies(page)
        }catch (e: Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }
    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        var response = try {
            api.getMovieDetails(movieId)
        }catch (e: Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getTrendingMovies(page: Int): Resource<TrendingMoviesList> {
        var response = try{
            api.getTrendingMovies(page = page)
        }catch (e:Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

}