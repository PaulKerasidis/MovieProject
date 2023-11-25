package com.example.movieproject.data.network.response.cast


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CastList(
    @SerialName("cast")
    val cast: List<Cast?>?,
    @SerialName("crew")
    val crew: List<Crew?>?,
    @SerialName("id")
    val id: Int?
)