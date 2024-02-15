package com.example.movieproject.movielist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.PopularMovies
import com.example.movieproject.data.network.response.TrendingMovie
import com.example.movieproject.data.network.response.cast.Cast
import com.example.movieproject.data.repository.MovieRepository
import com.example.movieproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
)  : ViewModel()  {

    private val _popularMovies  = MutableStateFlow(emptyList<Movies>())
    val popularMovies : StateFlow<List<Movies>> = _popularMovies.asStateFlow()


    private val _trendingMovies = MutableStateFlow(emptyList<TrendingMovie>())
    val trendingMovies : StateFlow<List<TrendingMovie>> = _trendingMovies.asStateFlow()

    private val _movieDetails : MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails.asStateFlow()

    private val _movieCast = MutableStateFlow(emptyList<Cast?>())
    val movieCast : StateFlow<List<Cast?>> = _movieCast.asStateFlow()

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
        viewModelScope.launch(){
            pagePopularMovies++
            val response = repository.getPopularMovies(pagePopularMovies)
            when(response){
                is Resource.Success -> _popularMovies.value = _popularMovies.value + response.data!!.results!!
                is Resource.Error -> "No data found"
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

//fun loadPopularMovies(){
//    viewModelScope.launch(){
//        pagePopularMovies++
//        val response = repository.getPopularMovies(pagePopularMovies).data?.results
//        if (response != null) {
//            _popularMovies.value = _popularMovies.value + response
//        }
//
//    }
//}