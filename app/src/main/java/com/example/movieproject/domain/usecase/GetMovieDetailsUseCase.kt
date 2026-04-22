package com.example.movieproject.domain.usecase

import com.example.movieproject.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieDetails(movieId)
}
