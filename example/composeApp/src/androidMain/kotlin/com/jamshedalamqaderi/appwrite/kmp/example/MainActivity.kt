package com.jamshedalamqaderi.appwrite.kmp.example

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jamshedalamqaderi.kmp.appwrite.AppwriteActivityLifecycleCallbacks

class MainActivity : ComponentActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerActivityLifecycleCallbacks(AppwriteActivityLifecycleCallbacks)
        setContent {
            App()
        }
    }
}
