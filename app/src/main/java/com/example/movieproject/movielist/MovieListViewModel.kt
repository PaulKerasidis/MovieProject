package com.example.movieproject.movielist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.TrendingMovie
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

    init {
        loadPopularMovies()
        loadTrendingMovies()
    }


    private fun loadPopularMovies(){
        viewModelScope.launch{
            val response = repository.getPopularMovies().data?.results
            if (response != null) {
                _popularMovies.value = response
            }
        }
    }

    private fun loadTrendingMovies(){
        viewModelScope.launch {
            val response =repository.getTrendingMovies().data?.results
            if (response != null) {
                _trendingMovies.value = response
            }
        }
    }

}