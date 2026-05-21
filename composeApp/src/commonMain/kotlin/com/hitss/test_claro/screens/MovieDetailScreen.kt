package com.hitss.test_claro.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.hitss.test_claro.components.DetailMovie
import com.hitss.test_claro.domain.model.MovieDTO
import com.hitss.test_claro.domain.remote.MovieService
import com.hitss.test_claro.domain.util.onFailure
import com.hitss.test_claro.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

class MovieDetailScreen(private val idMovie: Int): Screen {
    @Composable
    override fun Content() {
        val movieService: MovieService = koinInject()
        var movieDetail by remember {
            mutableStateOf<MovieDTO?>(null)
        }
        var resultText by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }
        var isError by remember { mutableStateOf(false) }

        isLoading = true

        CoroutineScope(Dispatchers.Default).launch {

            movieService.retrieveMovieDetails(idMovie).onSuccess { movie ->

                withContext(Dispatchers.Main) {
                    movieDetail = movie
                    isLoading = false
                    isError = false
                }
            }.onFailure {error ->
                withContext(Dispatchers.Main) {
                    isLoading = false
                    isError = true
                    resultText = "Error: $error"
                }
            }
        }

        AnimatedVisibility(true) {

            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                    .windowInsetsPadding(
                        WindowInsets.safeDrawing.only(WindowInsetsSides.Vertical)
                    ).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (isLoading) {
                    Text("Cargando...", color = Color.White)
                } else {
                    if(isError) {
                        Text(resultText, color = Color.White)
                    } else {
                        DetailMovie(movieDetail)
                    }
                }
            }
        }
    }
}