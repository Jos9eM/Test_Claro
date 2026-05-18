package com.hitss.test_claro.domain.remote

import com.hitss.test_claro.domain.model.MovieDTO
import com.hitss.test_claro.domain.model.MovieItem
import com.hitss.test_claro.domain.util.DataError
import com.hitss.test_claro.domain.util.Result

interface MovieService {

    suspend fun retrieveTopRatedMovies(): Result<List<MovieItem>, DataError.Remote>

    suspend fun retrieveMovieDetails(movieId: Int): Result<MovieDTO, DataError.Remote>
}