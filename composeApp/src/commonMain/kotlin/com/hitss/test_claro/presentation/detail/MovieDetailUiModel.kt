package com.hitss.test_claro.presentation.detail

data class MovieDetailUiModel(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val releaseDate: String,
    val rating: String,
    val duration: String
)