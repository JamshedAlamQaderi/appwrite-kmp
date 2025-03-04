package com.jamshedalamqaderi.kmp.appwrite

import kotlinx.datetime.Clock
import kotlin.random.Random

class ID {
    companion object {
        // Generate an hex ID based on timestamp
        // Recreated from https://www.php.net/manual/en/function.uniqid.php
        private fun hexTimestamp(): String {
            val now = Clock.System.now()
            val sec = now.epochSeconds.toString(16).padStart(8, '0')
            val usec = (Clock.System.now().nanosecondsOfSecond / 1000) % 1000

            val hexTimestamp =
                "${sec}${usec.toString(16).padStart(5, '0')}"

            return hexTimestamp
        }

        fun custom(id: String): String = id

        // Generate a unique ID with padding to have a longer ID
        fun unique(padding: Int = 7): String {
            val baseId = hexTimestamp()
            val randomPadding =
                (1..padding).joinToString("") {
                    Random.nextInt(0, 16).toString(16)
                }

            return baseId + randomPadding
        }
    }
}
