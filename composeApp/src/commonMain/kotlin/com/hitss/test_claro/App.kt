package com.hitss.test_claro

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.hitss.test_claro.screens.MovieListScreen

@Composable
@Preview
fun App() {
    MaterialTheme(colorScheme = DarkColors) {
        Navigator(screen = MovieListScreen())
    }
}