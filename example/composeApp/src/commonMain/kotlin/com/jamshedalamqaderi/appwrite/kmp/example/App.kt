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
import com.jamshedalamqaderi.kmp.appwrite.ID
import com.jamshedalamqaderi.kmp.appwrite.services.Account
import com.jamshedalamqaderi.kmp.appwrite.services.Databases
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.jetbrains.compose.ui.tooling.preview.Preview

const val DB_ID = "68a459b400178f65fb13"
const val STUDENT_COLLECTION = "68a459c20031a865d804"

@Composable
@Preview
fun App() {
    val client: Client =
        remember {
            Client()
                .setProject("68a1e08c00254a7e9714")
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
                        val doc = database.createDocument(
                            databaseId = DB_ID,
                            collectionId = STUDENT_COLLECTION,
                            documentId = ID.unique(),
                            data = buildJsonObject {
                                put("name", "Jamshed")
                                put("roll", 29)
                            }
                        )
                        println("created doc: $doc")
                        database.deleteDocument(
                            databaseId = DB_ID,
                            collectionId = STUDENT_COLLECTION,
                            documentId = doc.id
                        )
                        println("deleted doc: $doc")
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
