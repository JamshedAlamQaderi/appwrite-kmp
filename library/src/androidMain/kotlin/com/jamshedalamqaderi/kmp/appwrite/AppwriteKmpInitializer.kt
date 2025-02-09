package com.jamshedalamqaderi.kmp.appwrite

import android.annotation.SuppressLint
import android.content.Context
import androidx.startup.Initializer

class AppwriteKmpInitializer : Initializer<Unit> {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun create(context: Context) {
        Companion.context = context
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
