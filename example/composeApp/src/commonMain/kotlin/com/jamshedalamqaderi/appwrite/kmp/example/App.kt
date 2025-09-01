package com.jamshedalamqaderi.appwrite.kmp.example

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jamshedalamqaderi.kmp.appwrite.Client
import com.jamshedalamqaderi.kmp.appwrite.Query
import com.jamshedalamqaderi.kmp.appwrite.services.Account
import com.jamshedalamqaderi.kmp.appwrite.services.Databases
import com.jamshedalamqaderi.kmp.appwrite.services.Realtime
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

const val DB_ID = "68a459b400178f65fb13"
const val STUDENT_COLLECTION = "68a459c20031a865d804"

@Composable
@Preview
fun App() {
    val client: Client =
        remember {
            Client()
                .setEndpoint("https://cloud.appwrite.io/v1")
                .setProject("68a1e08c00254a7e9714")
        }
    val account = remember(client) { Account(client) }
    val database = remember(client) { Databases(client) }
    val realtime = remember(client) { Realtime(client) }
    val scope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        val updateSubscription =
            realtime.subscribe("databases.68a459b400178f65fb13.tables.teacher.rows.68b099cc00045dd527ab") {
                println("Received realtime update: ${it.channels}")
            }
        val createSubscription =
            realtime.subscribe("databases.68a459b400178f65fb13.tables.teacher.rows.68b099c200294b175ead") {
                println("Received realtime create: $it")
            }
        onDispose {
            updateSubscription.close()
            createSubscription.close()
        }
    }
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {
                scope.launch {
                    runCatching {
                        println("Session: ${account.createEmailPasswordSession("example@gmail.com", "abcd13579")}")
                    }.onFailure {
                        println("Exception: ${it.message}")
                    }
                }
            }) {
                Text("Create Session")
            }

            Button(onClick = {
                scope.launch {
                    runCatching {
                        val document = database.getDocument(
                            DB_ID,
                            "teacher",
                            "68b099c200294b175ead"
                        )
                        println("Document: $document")
                        val logs = account.listLogs(listOf(
                            Query.limit(2)
                        ))
                        println("Logs: $logs")
                    }.onFailure {
                        it.printStackTrace()
                    }
                }
            }) {
                Text("DB Test")
            }

            Button(onClick = {
                scope.launch {
                    val response = client.ping()
                    println("Ping response: $response")
                }
            }) {
                Text("Ping")
            }
        }
    }
}
