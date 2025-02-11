package com.jamshedalamqaderi.appwrite.kmp.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.enums.OAuthProvider
import com.jamshedalamqaderi.kmp.appwrite.services.Account
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val client: Client =
        remember {
            Client()
                .setEndpoint("https://")
                .setProject("6797667e0013cde0eb41")
        }
    val account = remember(client) { Account(client) }
    val scope = rememberCoroutineScope()

    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {
                scope.launch {
                    runCatching {
                        account.createOAuth2Token(OAuthProvider.GOOGLE)
                    }.onFailure {
                        println("Exception: ${it.message}")
                    }
                }
            }) {
                Text("Login with Google")
            }
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                scope.launch {
                    runCatching {
                        val currentUser = account.get()
                        println("Current User: $currentUser")
                    }.onFailure {
                        println("Exception while retrieving current user: ${it.message}")
                    }
                }
            }) {
                Text("Get current User")
            }
        }
    }
}
