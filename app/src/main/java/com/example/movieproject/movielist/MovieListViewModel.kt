package com.example.movieproject.movielist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.TrendingMovie
import com.example.movieproject.data.network.response.cast.Cast
import com.example.movieproject.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
)  : ViewModel()  {

    private val _popularMovies = mutableStateOf<List<Movies>>(listOf())
    val popularMovies : State<List<Movies>> = _popularMovies

    var loadError = mutableStateOf("")

    private val _trendingMovies = mutableStateOf<List<TrendingMovie>>(listOf())
    val trendingMovies : State<List<TrendingMovie>> = _trendingMovies

    private val _movieDetails = mutableStateOf<MovieDetails>(MovieDetails(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null))
    val movieDetails: State<MovieDetails> = _movieDetails

    private val _movieCast = mutableStateOf<List<Cast?>>(listOf())
    val movieCast : State<List<Cast?>> = _movieCast

    private var pagePopularMovies : Int = 0
    private var pageTrendingMovies : Int = 0

    init {
        loadPopularMovies()
        loadTrendingMovies()
    }


    fun loadMovieCast(movieId: Int){
        viewModelScope.launch{
            val response = repository.getMovieCast(movieId).data?.cast
            if (response != null) {
                _movieCast.value = response
            }
        }
    }

    fun loadPopularMovies(){
        viewModelScope.launch{
            pagePopularMovies++
            val response = repository.getPopularMovies(pagePopularMovies).data?.results
            if (response != null) {
                _popularMovies.value = _popularMovies.value + response
            }
        }
    }

    fun loadTrendingMovies(){
        viewModelScope.launch {
            pageTrendingMovies++
            val response = repository.getTrendingMovies(pageTrendingMovies).data?.results
            if (response != null) {
                _trendingMovies.value = _trendingMovies.value + response
            }
        }
    }

    fun loadMovieDetails(movieId: Int){
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId).data
            if (response != null) {
                _movieDetails.value = response
            }
        }
    }

}

