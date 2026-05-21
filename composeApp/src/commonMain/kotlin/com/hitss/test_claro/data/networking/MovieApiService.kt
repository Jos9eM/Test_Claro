package com.hitss.test_claro.data.networking

import com.hitss.test_claro.data.mappers.toImageFullUrls
import com.hitss.test_claro.data.mappers.toMovieItem
import com.hitss.test_claro.domain.model.MovieDTO
import com.hitss.test_claro.domain.model.MovieItem
import com.hitss.test_claro.domain.model.MovieList
import com.hitss.test_claro.domain.remote.MovieService
import com.hitss.test_claro.domain.util.DataError
import com.hitss.test_claro.domain.util.Result
import com.hitss.test_claro.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.headers

class MovieApiService(private val client: HttpClient) : MovieService {

    override suspend fun retrieveTopRatedMovies(): Result<List<MovieItem>, DataError.Remote> {
        return client.get<MovieList>(
            route = "/movie/top_rated",
            queryParams = mapOf("language" to "es-MX", "page" to 1)
        ) {
            headers {
                append("Authorization", "Bearer ${UrlConstants.API_TOKEN}")
                append("accept", "application/json")
                append("Accept-Encoding", "identity")
            }
        }.map { movieList ->
            movieList.results.map { movieDTO -> movieDTO.toMovieItem() }
        }
    }

    override suspend fun retrieveMovieDetails(movieId: Int): Result<MovieDTO, DataError.Remote> {
        return client.get<MovieDTO>(
            route = "/movie/$movieId", queryParams = mapOf("language" to "es-MX")
        ) {
            headers {
                append("Authorization", "Bearer ${UrlConstants.API_TOKEN}")
                append("accept", "application/json")
                append("Accept-Encoding", "identity")

            }
        }.map { movieDTO ->
            movieDTO.toImageFullUrls()
        }
    }
}