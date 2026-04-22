package com.example.movieproject.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.data.network.response.Movies
import com.example.movieproject.data.network.response.TrendingMovie
import com.example.movieproject.domain.usecase.DiscoverMoviesUseCase
import com.example.movieproject.domain.usecase.GetPopularMoviesUseCase
import com.example.movieproject.domain.usecase.GetTrendingMoviesUseCase
import com.example.movieproject.domain.usecase.SearchMoviesUseCase
import com.example.movieproject.utils.Constants
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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val getTrendingMovies: GetTrendingMoviesUseCase,
    private val discoverMovies: DiscoverMoviesUseCase,
    private val searchMovies: SearchMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

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

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadMoreMovies -> loadMoreMovies()
            is HomeIntent.LoadMoreTrending -> loadMoreTrending()
            is HomeIntent.ToggleSearch -> toggleSearch()
            is HomeIntent.SearchMovies -> onSearchQueryChanged(intent.query)
            is HomeIntent.SelectGenre -> onGenreSelected(intent.index)
        }
    }

    private fun toggleSearch() {
        val isActive = _state.value.isSearchActive
        _state.update { it.copy(isSearchActive = !isActive) }
        if (isActive) onSearchQueryChanged("")
    }

    private fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            resetAndLoad()
        }
    }

    private fun onGenreSelected(index: Int) {
        _state.update { it.copy(selectedGenreIndex = index, searchQuery = "", isSearchActive = false) }
        resetAndLoad()
    }

    private fun loadMoreMovies() {
        if (isLoadingMovies || !hasMoreMovies) return
        isLoadingMovies = true
        val isFirstPage = currentPage == 0
        if (!isFirstPage) _state.update { it.copy(isPaginating = true) }
        currentPage++
        val query = _state.value.searchQuery
        val genreId = Constants.GENRES[_state.value.selectedGenreIndex].second
        viewModelScope.launch {
            val result = when {
                query.isNotBlank() -> searchMovies(query = query, page = currentPage)
                genreId != null -> discoverMovies(page = currentPage, genreId = genreId)
                else -> getPopularMovies(page = currentPage)
            }
            when (result) {
                is Resource.Success -> {
                    val newMovies = result.data?.results ?: emptyList()
                    allMovies = allMovies + newMovies
                    hasMoreMovies = newMovies.isNotEmpty()
                    _state.update { it.copy(moviesState = UiState.Success(allMovies), isPaginating = false) }
                }
                is Resource.Error -> {
                    if (isFirstPage) {
                        _state.update { it.copy(moviesState = UiState.Error(result.message ?: "Unknown error"), isPaginating = false) }
                    } else {
                        _state.update { it.copy(isPaginating = false) }
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
        _state.update { it.copy(moviesState = UiState.Loading) }
        loadMoreMovies()
    }

    private fun loadMoreTrending() {
        trendingPage++
        viewModelScope.launch {
            val result = getTrendingMovies(trendingPage)
            when (result) {
                is Resource.Success -> {
                    val newTrending = result.data?.results ?: emptyList()
                    allTrending = allTrending + newTrending
                    _state.update { it.copy(trendingState = UiState.Success(allTrending)) }
                }
                is Resource.Error -> {
                    if (trendingPage == 1) {
                        _state.update { it.copy(trendingState = UiState.Error(result.message ?: "Unknown error")) }
                    }
                }
            }
        }
    }
}
