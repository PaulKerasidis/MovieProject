package com.example.movieproject.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> = try {
    Resource.Success(apiCall())
} catch (e: Exception) {
    Resource.Error(e.localizedMessage ?: "An unknown error occurred")
}
