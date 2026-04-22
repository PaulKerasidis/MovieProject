package com.example.movieproject.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.TrendingMovie
import com.example.movieproject.data.repository.MovieRepository
import com.example.movieproject.utils.Resource
import com.example.movieproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    private val _detailState = MutableStateFlow(DetailUiState())
    val detailState: StateFlow<DetailUiState> = _detailState.asStateFlow()

    private var allMovies = emptyList<Movies>()
    private var allTrending = emptyList<TrendingMovie>()
    private var currentPage = 0
    private var trendingPage = 0
    private var isLoadingMovies = false
    private var hasMoreMovies = true
    private var searchJob: Job? = null

    init {
        loadMoreMovies()
        loadMoreTrending()
    }

    fun onIntent(intent: MovieIntent) {
        when (intent) {
            is MovieIntent.LoadMoreMovies -> loadMoreMovies()
            is MovieIntent.LoadMoreTrending -> loadMoreTrending()
            is MovieIntent.ToggleSearch -> toggleSearch()
            is MovieIntent.SearchMovies -> onSearchQueryChanged(intent.query)
            is MovieIntent.SelectGenre -> onGenreSelected(intent.index)
            is MovieIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId)
            is MovieIntent.LoadMovieCast -> loadMovieCast(intent.movieId)
        }
    }

    private fun toggleSearch() {
        val isActive = _homeState.value.isSearchActive
        _homeState.update { it.copy(isSearchActive = !isActive) }
        if (isActive) onSearchQueryChanged("")
    }

    private fun onSearchQueryChanged(query: String) {
        _homeState.update { it.copy(searchQuery = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            resetAndLoad()
        }
    }

    private fun onGenreSelected(index: Int) {
        _homeState.update { it.copy(selectedGenreIndex = index, searchQuery = "", isSearchActive = false) }
        resetAndLoad()
    }

    private fun loadMoreMovies() {
        if (isLoadingMovies || !hasMoreMovies) return
        isLoadingMovies = true
        val isFirstPage = currentPage == 0
        if (!isFirstPage) _homeState.update { it.copy(isPaginating = true) }
        currentPage++
        val query = _homeState.value.searchQuery
        val genreId = GENRES[_homeState.value.selectedGenreIndex].second
        viewModelScope.launch {
            val result = when {
                query.isNotBlank() -> repository.searchMovies(query = query, page = currentPage)
                genreId != null -> repository.discoverMovies(page = currentPage, genreId = genreId)
                else -> repository.getPopularMovies(page = currentPage)
            }
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data?.results ?: emptyList()
                    allMovies = allMovies + newMovies
                    hasMoreMovies = newMovies.isNotEmpty()
                    _homeState.update { it.copy(moviesState = UiState.Success(allMovies), isPaginating = false) }
                }
                is Resource.Error -> {
                    if (isFirstPage) {
                        _homeState.update { it.copy(moviesState = UiState.Error(result.message ?: "Unknown error"), isPaginating = false) }
                    } else {
                        _homeState.update { it.copy(isPaginating = false) }
                    }
                }
            }
            isLoadingMovies = false
        }
    }

    private fun resetAndLoad() {
        currentPage = 0
        hasMoreMovies = true
        isLoadingMovies = false
        allMovies = emptyList()
        _homeState.update { it.copy(moviesState = UiState.Loading) }
        loadMoreMovies()
    }

    private fun loadMoreTrending() {
        trendingPage++
        viewModelScope.launch {
            val result = repository.getTrendingMovies(trendingPage)
            when (result) {
                is Resource.Success -> {
                    val newTrending = result.data?.results ?: emptyList()
                    allTrending = allTrending + newTrending
                    _homeState.update { it.copy(trendingState = UiState.Success(allTrending)) }
                }
                is Resource.Error -> {
                    if (trendingPage == 1) {
                        _homeState.update { it.copy(trendingState = UiState.Error(result.message ?: "Unknown error")) }
                    }
                }
            }
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        _detailState.update { it.copy(movieDetailsState = UiState.Loading) }
        viewModelScope.launch {
            when (val result = repository.getMovieDetails(movieId)) {
                is Resource.Success -> _detailState.update { it.copy(movieDetailsState = UiState.Success(result.data!!)) }
                is Resource.Error -> _detailState.update { it.copy(movieDetailsState = UiState.Error(result.message ?: "Unknown error")) }
            }
        }
    }

    private fun loadMovieCast(movieId: Int) {
        _detailState.update { it.copy(castState = UiState.Loading) }
        viewModelScope.launch {
            when (val result = repository.getMovieCast(movieId)) {
                is Resource.Success -> _detailState.update { it.copy(castState = UiState.Success(result.data?.cast ?: emptyList())) }
                is Resource.Error -> _detailState.update { it.copy(castState = UiState.Error(result.message ?: "Unknown error")) }
            }
        }
    }
}
