package com.jamshedalamqaderi.appwrite.kmp.sample

import com.jamshedalamqaderi.appwrite.kmp.Client
import com.jamshedalamqaderi.appwrite.kmp.ID
import com.jamshedalamqaderi.appwrite.kmp.services.Account
import com.jamshedalamqaderi.appwrite.kmp.services.Storage
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

suspend fun configAppwrite() {
    val client = Client().setProject("6797667e0013cde0eb41")
    val account = Account(client)
    try {
        val session = account.createAnonymousSession()
        println("Session: $session")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
    val storage = Storage(client)
    val path = Path("../test_image.png")
    println("Is file iexists: ${SystemFileSystem.exists(path)}")
    storage.createFile("679772df001b5e3cab0e", ID.unique(), path, onProgress = {
        println("Progress: ${(it.completedBytes.toDouble() / it.total) * 100.0}")
    })
}