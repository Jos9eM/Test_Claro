package com.hitss.test_claro

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform