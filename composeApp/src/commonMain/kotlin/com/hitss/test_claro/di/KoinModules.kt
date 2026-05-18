package com.hitss.test_claro.di

import com.hitss.test_claro.data.networking.MovieApiService
import com.hitss.test_claro.domain.remote.MovieService
import com.hitss.test_claro.domain.usecase.GetMovieDetailUseCase
import com.hitss.test_claro.domain.usecase.GetTopRatedMoviesUseCase
import com.hitss.test_claro.presentation.detail.MovieDetailViewModel
import com.hitss.test_claro.presentation.movielist.MovieListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val sharedModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                })
            }
        }
    }

    single<MovieService> { MovieApiService(get()) }

    factory { GetTopRatedMoviesUseCase(get()) }
    factory { GetMovieDetailUseCase(get()) }

    factory { MovieListViewModel(get()) }
    factory { MovieDetailViewModel(get()) }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(sharedModule)
    }
}

fun initKoinIos() {
    initKoin()
}