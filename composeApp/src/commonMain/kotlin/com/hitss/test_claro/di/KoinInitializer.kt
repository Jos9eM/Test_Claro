package com.hitss.test_claro.di

object KoinInitializer {
    private var initialized = false

    fun init() {
        if (!initialized) {
            initKoin()
            initialized = true
        }
    }
}