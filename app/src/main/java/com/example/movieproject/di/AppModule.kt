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
import com.example.movieproject.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideMovieRepository(api: MovieApi):MovieRepository = MovieRepositoryImp(api)

    @Singleton
    @Provides
    fun provideMovieApi(): MovieApi {
        val client = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor { Timber.tag("movie").v(it) }
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logging)
                }
            }
            .build()

        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(BASE_URL)
            .build()
            .create(MovieApi::class.java)
    }

}