package com.example.movieproject.data.domain

import android.util.Log
import com.example.movieproject.data.network.api.MovieApi
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.data.network.response.TrendingMoviesList
import com.example.movieproject.data.network.response.cast.CastList
import com.example.movieproject.data.repository.MovieRepository
import com.example.movieproject.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityScoped
class MovieRepositoryImp @Inject constructor(
    private val movieApi: MovieApi
):MovieRepository {

    override suspend fun getPopularMovies(page: Int): Resource<PopularMovies> {
        var response = try {
            movieApi.getPopularMovies(page = page)
        }catch (e: Exception){
            return Resource.Error("An API Error occured")
        }
        return Resource.Success(response)
    }


    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        var response = try {
            movieApi.getMovieDetails(movieId)
        }catch (e: Exception){
            Log.e("MovieDetailsRepository", "Error fetching movie details", e)
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getTrendingMovies(page: Int): Resource<TrendingMoviesList> {
        var response = try{
            movieApi.getTrendingMovies(page = page)
        }catch (e:Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    override suspend fun getMovieCast(movieId: Int): Resource<CastList> {
        var response = try{
            movieApi.getMovieCast(movieId)
        }catch (e:Exception){
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

}