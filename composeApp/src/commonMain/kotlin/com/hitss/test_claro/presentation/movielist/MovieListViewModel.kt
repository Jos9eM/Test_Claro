package com.hitss.test_claro.presentation.movielist

import com.hitss.test_claro.domain.usecase.GetTopRatedMoviesUseCase
import com.hitss.test_claro.domain.util.onFailure
import com.hitss.test_claro.domain.util.onSuccess
import com.hitss.test_claro.presentation.common.MovieListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val getTopRatedMovies: GetTopRatedMoviesUseCase
) {
    private val _state = MutableStateFlow(MovieListState())
    val state = _state.asStateFlow()

    fun loadMovies() {
        CoroutineScope(Dispatchers.Default).launch {

            _state.update { it.copy(isLoading = true, error = null) }

            getTopRatedMovies().onSuccess { movies ->
                    _state.update {
                        it.copy(
                            movies = movies, isLoading = false
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