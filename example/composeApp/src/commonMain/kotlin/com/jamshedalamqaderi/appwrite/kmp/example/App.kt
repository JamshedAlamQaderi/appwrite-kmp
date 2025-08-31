package com.jamshedalamqaderi.appwrite.kmp.example

import androidx.compose.foundation.layout.*
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
import com.jamshedalamqaderi.kmp.appwrite.Query
import com.jamshedalamqaderi.kmp.appwrite.services.Account
import com.jamshedalamqaderi.kmp.appwrite.services.Databases
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import org.jetbrains.compose.ui.tooling.preview.Preview

const val DB_ID = "68a459b400178f65fb13"
const val STUDENT_COLLECTION = "68a459c20031a865d804"

@Composable
@Preview
fun App() {
    val client: Client = remember {
        Client().setProject("68a1e08c00254a7e9714")
    }
    val account = remember(client) { Account(client) }
    val database = remember(client) { Databases(client) }
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
                        println("Session: ${account.createEmailPasswordSession("example@gmail.com", "abcd13579")}")
                    }.onFailure {
                        println("Exception: ${it.message}")
                    }
                }
            }) {
                Text("Create Session")
            }
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                scope.launch {
                    runCatching {
                        val orQueries = listOf(
                            Query.or(
                                listOf(
                                    Query.equal("name", "Jamshed"),
                                    Query.equal("name", "Sadia")
                                )
                            )
                        )
                        val andQueries = listOf(
                            Query.and(
                                listOf(
                                    Query.equal("name", "Jamshed"),
                                    Query.equal("name", "Shahed"),
                                )
                            )
                        )
                        println("Or Queries: $orQueries")
                        println("And Queries: $andQueries")
                    }.onFailure {
                        println("Exception while retrieving current user: ${it.message}")
                    }
                }
            }) {
                Text("Do DB Operation")
            }
        }
    }
}
