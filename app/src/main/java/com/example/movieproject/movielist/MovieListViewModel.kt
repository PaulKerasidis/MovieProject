package com.example.movieproject.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.network.response.MovieDetails
import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.TrendingMovie
import com.example.movieproject.data.network.response.cast.Cast
import com.example.movieproject.data.repository.MovieRepository
import com.example.movieproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

val GENRES = listOf(
    "All" to null,
    "Action" to 28,
    "Comedy" to 35,
    "Drama" to 18,
    "Romance" to 10749,
    "Sci-Fi" to 878,
)

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow(emptyList<Movies>())
    val movies: StateFlow<List<Movies>> = _movies.asStateFlow()

    private val _trendingMovies = MutableStateFlow(emptyList<TrendingMovie>())
    val trendingMovies: StateFlow<List<TrendingMovie>> = _trendingMovies.asStateFlow()

    private val _movieDetails: MutableStateFlow<MovieDetails?> = MutableStateFlow(null)
    val movieDetails: StateFlow<MovieDetails?> = _movieDetails.asStateFlow()

    private val _movieCast = MutableStateFlow(emptyList<Cast?>())
    val movieCast: StateFlow<List<Cast?>> = _movieCast.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive.asStateFlow()

    private val _selectedGenreIndex = MutableStateFlow(0)
    val selectedGenreIndex: StateFlow<Int> = _selectedGenreIndex.asStateFlow()

    private var currentPage = 0
    private var isLoading = false
    private var hasMore = true
    private var searchJob: Job? = null

    private var trendingPage = 0

    init {
        loadMoreMovies()
        loadTrendingMovies()
    }

    fun toggleSearch() {
        _isSearchActive.value = !_isSearchActive.value
        if (!_isSearchActive.value) {
            onSearchQueryChanged("")
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            resetAndLoad()
        }
    }

    fun onGenreSelected(index: Int) {
        _selectedGenreIndex.value = index
        _searchQuery.value = ""
        _isSearchActive.value = false
        resetAndLoad()
    }

    fun loadMoreMovies() {
        if (isLoading || !hasMore) return
        isLoading = true
        currentPage++
        val query = _searchQuery.value
        val genreId = GENRES[_selectedGenreIndex.value].second
        viewModelScope.launch {
            val result = when {
                query.isNotBlank() -> repository.searchMovies(query = query, page = currentPage)
                genreId != null -> repository.discoverMovies(page = currentPage, genreId = genreId)
                else -> repository.getPopularMovies(page = currentPage)
            }
            if (result is Resource.Success) {
                val newMovies = result.data?.results ?: emptyList()
                _movies.value = _movies.value + newMovies
                hasMore = newMovies.isNotEmpty()
            }
            isLoading = false
        }
    }

    private fun resetAndLoad() {
        currentPage = 0
        hasMore = true
        isLoading = false
        _movies.value = emptyList()
        loadMoreMovies()
    }

    fun loadTrendingMovies() {
        viewModelScope.launch {
            trendingPage++
            val response = repository.getTrendingMovies(trendingPage).data?.results
            if (response != null) {
                _trendingMovies.value = _trendingMovies.value + response
            }
        }
    }

    fun loadMovieCast(movieId: Int) {
        viewModelScope.launch {
            val response = repository.getMovieCast(movieId).data?.cast
            if (response != null) {
                _movieCast.value = response
            }
        }
    }

    fun loadMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId).data
            if (response != null) {
                _movieDetails.value = response
            }
        }
    }
}
