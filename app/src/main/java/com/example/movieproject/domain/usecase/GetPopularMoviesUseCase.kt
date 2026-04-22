package com.example.movieproject.domain.usecase

import com.example.movieproject.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int) = repository.getPopularMovies(page)
}
