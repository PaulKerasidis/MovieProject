package com.example.movieproject.di

import com.example.movieproject.data.domain.MovieRepositoryImp
import com.example.movieproject.data.network.api.MovieApi
import com.example.movieproject.data.repository.MovieRepository
import com.example.movieproject.utils.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMovieRepository(
        api: MovieApi
    ):MovieRepository = MovieRepositoryImp(api)

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi{
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/Jason".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }

}