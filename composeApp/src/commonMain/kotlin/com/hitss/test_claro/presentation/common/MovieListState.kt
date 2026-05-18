package com.hitss.test_claro.presentation.common

import com.hitss.test_claro.domain.model.MovieItem

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<MovieItem> = emptyList(),
    val error: String? = null
)