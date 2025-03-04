package com.jamshedalamqaderi.kmp.appwrite.views

import android.app.Activity
import android.os.Bundle
import com.jamshedalamqaderi.kmp.appwrite.WebAuthComponent

class CallbackActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent?.data
        val scheme = url?.scheme
        if (scheme != null) {
            // Found a scheme, try to callback to web auth component.
            // Will only succeed if the scheme matches one launched by this sdk.
            WebAuthComponent.onCallback(scheme, url)
        }
        finish()
    }
}
