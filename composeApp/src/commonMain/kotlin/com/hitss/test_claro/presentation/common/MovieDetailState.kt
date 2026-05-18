package com.hitss.test_claro.presentation.common

import com.hitss.test_claro.domain.model.MovieDTO

data class MovieDetailState(
    val isLoading: Boolean = false, val movie: MovieDTO? = null, val error: String? = null
)