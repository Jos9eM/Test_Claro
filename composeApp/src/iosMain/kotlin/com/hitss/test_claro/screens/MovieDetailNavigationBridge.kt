package com.hitss.test_claro.screens

object MovieDetailNavigationBridge {

    private var onOpenMovieDetail: ((Int) -> Unit)? = null

    fun setOnOpenMovieDetail(callback: (Int) -> Unit) {
        onOpenMovieDetail = callback
    }

    fun openMovieDetail(movieId: Int) {
        onOpenMovieDetail?.invoke(movieId)
    }

    fun clearOnOpenMovieDetail() {
        onOpenMovieDetail = null
    }
}