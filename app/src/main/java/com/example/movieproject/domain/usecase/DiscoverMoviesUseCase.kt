package com.example.movieproject.domain.usecase

import com.example.movieproject.domain.repository.MovieRepository
import javax.inject.Inject

class DiscoverMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(page: Int, genreId: Int) = repository.discoverMovies(page, genreId)
}
