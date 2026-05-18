package com.hitss.test_claro.domain.model

import kotlinx.serialization.SerialName

data class MovieItem(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("poster_path") val posterPath: String,
    @SerialName("backdrop_path") val backdropPath: String,
    @SerialName("overview") val overview: String
)