package com.hitss.test_claro

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hitss.test_claro.domain.remote.MovieService
import com.hitss.test_claro.domain.util.onFailure
import com.hitss.test_claro.domain.util.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MaterialTheme {

        val movieService: MovieService = koinInject()

        var showContent by remember { mutableStateOf(false) }
        var resultText by remember { mutableStateOf("") }
        var isLoading by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding().fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Button(
                onClick = {
                    showContent = true
                    isLoading = true

                    CoroutineScope(Dispatchers.Default).launch {

                        movieService.retrieveTopRatedMovies().onSuccess { movies ->

                                val titles = movies.joinToString("\n") {
                                    it.title
                                }

                                withContext(Dispatchers.Main) {
                                    resultText = titles
                                    isLoading = false
                                }
                            }.onFailure { error ->

                                withContext(Dispatchers.Main) {
                                    resultText = "Error: $error"
                                    isLoading = false
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
                        Text("Cargando...")
                    } else {
                        Text(resultText)
                    }
                }
            }
        }
    }
}