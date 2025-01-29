package com.jamshedalamqaderi.appwrite.kmp.example

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
