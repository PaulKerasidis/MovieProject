package com.example.movieproject.data.repository

import com.example.movieproject.data.network.api.MovieApi
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.data.network.response.TrendingMoviesList
import com.example.movieproject.data.network.response.cast.CastList
import com.example.movieproject.domain.repository.MovieRepository
import com.example.movieproject.utils.Resource
import com.example.movieproject.utils.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImp @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getPopularMovies(page: Int): Resource<PopularMovies> =
        safeApiCall { movieApi.getPopularMovies(page = page) }

    override suspend fun discoverMovies(page: Int, genreId: Int): Resource<PopularMovies> =
        safeApiCall { movieApi.discoverMovies(page = page, genreId = genreId) }

    override suspend fun searchMovies(query: String, page: Int): Resource<PopularMovies> =
        safeApiCall { movieApi.searchMovies(query = query, page = page) }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> =
        safeApiCall { movieApi.getMovieDetails(movieId) }

    override suspend fun getTrendingMovies(page: Int): Resource<TrendingMoviesList> =
        safeApiCall { movieApi.getTrendingMovies(page = page) }

    override suspend fun getMovieCast(movieId: Int): Resource<CastList> =
        safeApiCall { movieApi.getMovieCast(movieId) }
}
