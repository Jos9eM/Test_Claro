package com.hitss.test_claro

import androidx.compose.ui.window.ComposeUIViewController
import com.hitss.test_claro.di.KoinInitializer

fun MainViewController() = ComposeUIViewController {
    KoinInitializer.init()
    App()
}