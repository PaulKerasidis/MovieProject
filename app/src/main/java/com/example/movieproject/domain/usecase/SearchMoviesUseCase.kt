package com.example.movieproject.domain.usecase

import com.example.movieproject.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(query: String, page: Int) = repository.searchMovies(query, page)
}
