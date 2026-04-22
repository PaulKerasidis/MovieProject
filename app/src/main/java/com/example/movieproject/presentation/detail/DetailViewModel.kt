package com.example.movieproject.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.domain.usecase.GetMovieCastUseCase
import com.example.movieproject.domain.usecase.GetMovieDetailsUseCase
import com.example.movieproject.utils.Resource
import com.example.movieproject.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val getMovieCast: GetMovieCastUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _state = MutableStateFlow(DetailUiState())
    val state: StateFlow<DetailUiState> = _state.asStateFlow()

    init {
        loadDetails()
        loadCast()
    }

    fun onIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.Retry -> { loadDetails(); loadCast() }
        }
    }

    private fun loadDetails() {
        _state.update { it.copy(movieDetailsState = UiState.Loading) }
        viewModelScope.launch {
            when (val result = getMovieDetails(movieId)) {
                is Resource.Success -> _state.update { it.copy(movieDetailsState = UiState.Success(result.data!!)) }
                is Resource.Error -> _state.update { it.copy(movieDetailsState = UiState.Error(result.message ?: "Unknown error")) }
            }
        }
    }

    private fun loadCast() {
        _state.update { it.copy(castState = UiState.Loading) }
        viewModelScope.launch {
            when (val result = getMovieCast(movieId)) {
                is Resource.Success -> _state.update { it.copy(castState = UiState.Success(result.data?.cast ?: emptyList())) }
                is Resource.Error -> _state.update { it.copy(castState = UiState.Error(result.message ?: "Unknown error")) }
            }
        }
    }
}
