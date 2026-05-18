package com.hitss.test_claro.data.mappers

import com.hitss.test_claro.data.networking.UrlConstants
import com.hitss.test_claro.domain.model.MovieDTO
import com.hitss.test_claro.domain.model.MovieItem

fun MovieDTO.toMovieItem(): MovieItem {
    return MovieItem(
        id = id,
        title = title,
        posterPath = posterPath.toTmdbImageUrl(),
        backdropPath = backdropPath.toTmdbImageUrl(),
        overview = overview
    )
}

fun MovieDTO.toImageFullUrls(): MovieDTO {
    return this.copy(
        posterPath = this.posterPath.toTmdbImageUrl(),
        backdropPath = this.backdropPath.toTmdbImageUrl()
    )
}

fun String?.toTmdbImageUrl(): String {
    return if (this.isNullOrBlank()) {
        ""
    } else {
        "${UrlConstants.BASE_URL_IMAGES}$this"
    }
}