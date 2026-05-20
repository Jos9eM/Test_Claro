package com.hitss.test_claro.presentation.detail

import com.hitss.test_claro.di.KoinInitializer
import com.hitss.test_claro.domain.model.MovieDTO
import com.hitss.test_claro.domain.usecase.GetMovieDetailUseCase
import com.hitss.test_claro.domain.util.onFailure
import com.hitss.test_claro.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.mp.KoinPlatform.getKoin
import kotlin.math.round

class MovieDetailBridge {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun fetchMovieDetail(
        movieId: Int,
        onSuccess: (MovieDetailUiModel) -> Unit,
        onError: (String) -> Unit
    ) {
        KoinInitializer.init()

        val getMovieDetailUseCase: GetMovieDetailUseCase = getKoin().get()

        scope.launch {
            getMovieDetailUseCase(movieId)
                .onSuccess { movieDTO ->
                    onSuccess(movieDTO.toMovieDetailUiModel())
                }
                .onFailure { error ->
                    onError(error.name)
                }
        }
    }

    fun clear() {
        scope.cancel()
    }
}

private fun MovieDTO.toMovieDetailUiModel(): MovieDetailUiModel {
    val roundedRating = round(voteAverage * 10.0) / 10.0

    return MovieDetailUiModel(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterUrl = posterPath.orEmpty(),
        backdropUrl = backdropPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        rating = roundedRating.toString(),
        duration = runtime?.let { "$it min" } ?: "Duración no disponible"
    )
}