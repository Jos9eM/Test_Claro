package com.hitss.test_claro.domain.usecase

import com.hitss.test_claro.domain.model.MovieDTO
import com.hitss.test_claro.domain.model.MovieItem
import com.hitss.test_claro.domain.remote.MovieService
import com.hitss.test_claro.domain.util.DataError
import com.hitss.test_claro.domain.util.Result

class GetTopRatedMoviesUseCase(
    private val service: MovieService
) {
    suspend operator fun invoke(): Result<List<MovieItem>, DataError.Remote> {
        return service.retrieveTopRatedMovies()
    }
}

class GetMovieDetailUseCase(
    private val service: MovieService
) {
    suspend operator fun invoke(
        movieId: Int
    ): Result<MovieDTO, DataError.Remote> {
        return service.retrieveMovieDetails(movieId)
    }
}