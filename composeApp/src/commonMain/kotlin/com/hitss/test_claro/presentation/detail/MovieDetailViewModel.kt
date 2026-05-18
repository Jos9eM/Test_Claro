package com.hitss.test_claro.presentation.detail

import com.hitss.test_claro.domain.usecase.GetMovieDetailUseCase
import com.hitss.test_claro.domain.util.onFailure
import com.hitss.test_claro.domain.util.onSuccess
import com.hitss.test_claro.presentation.common.MovieDetailState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieDetail: GetMovieDetailUseCase
) {

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    fun loadMovie(movieId: Int) {
        CoroutineScope(Dispatchers.Default).launch {

            _state.update { it.copy(isLoading = true, error = null) }

            getMovieDetail(movieId).onSuccess { movie ->
                _state.update {
                    it.copy(
                        movie = movie, isLoading = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false, error = error.toString()
                    )
                }
            }
        }
    }
}