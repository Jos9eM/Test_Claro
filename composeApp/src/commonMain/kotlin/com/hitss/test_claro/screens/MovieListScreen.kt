package com.hitss.test_claro.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.hitss.test_claro.components.CarouselCard
import com.hitss.test_claro.domain.model.MovieItem
import com.hitss.test_claro.domain.remote.MovieService
import com.hitss.test_claro.domain.util.onFailure
import com.hitss.test_claro.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

class MovieListScreen: Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        val movieService: MovieService = koinInject()
        var movieList by rememberSaveable {
            mutableStateOf<List<MovieItem>>(emptyList())
        }
        var showContent by rememberSaveable { mutableStateOf(false) }
        var resultText by rememberSaveable { mutableStateOf("") }
        var isLoading by rememberSaveable { mutableStateOf(false) }
        var isError by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding().fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                onClick = {
                    showContent = true
                    isLoading = true

                    CoroutineScope(Dispatchers.IO).launch {

                        movieService.retrieveTopRatedMovies().onSuccess { movies ->

                            withContext(Dispatchers.Main) {
                                isLoading = false
                                isError = false
                                movieList = movies

                            }
                        }.onFailure { error ->
                            withContext(Dispatchers.Main) {
                                isLoading = false
                                isError = true
                                resultText = "Error: $error"
                            }
                        }
                    }
                }) {
                Text("Obtener películas")
            }

            AnimatedVisibility(showContent) {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (isLoading) {
                        Text("Cargando...", color = Color.White)
                    } else {
                        if(isError) {
                            Text(resultText, color = Color.White)
                        } else {
                            CarouselCard(movieList) { idMovie ->
                                navigator?.let { nav ->
                                    openMovieDetail(idMovie.id, nav)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

expect fun openMovieDetail(movieId: Int, navigator: Navigator)