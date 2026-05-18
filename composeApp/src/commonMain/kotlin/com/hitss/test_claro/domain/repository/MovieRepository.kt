package com.hitss.test_claro.domain.repository

import com.hitss.test_claro.domain.model.MovieDTO

interface MovieRepository {
    suspend fun getTopRatedMovies(): List<MovieDTO>

    suspend fun getMovieDetails(movieId: Int): MovieDTO
}

