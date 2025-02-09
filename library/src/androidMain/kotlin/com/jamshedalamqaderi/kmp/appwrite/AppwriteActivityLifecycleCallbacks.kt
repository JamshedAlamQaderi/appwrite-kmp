package com.jamshedalamqaderi.kmp.appwrite

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle

@SuppressLint("NewApi", "StaticFieldLeak")
object AppwriteActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private var currentActivity: Activity? = null

    fun getActivity(): Activity? = currentActivity

    override fun onActivityCreated(
        activity: Activity,
        savedInstanceState: Bundle?,
    ) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        // Do nothing
    }

    override fun onActivityStopped(activity: Activity) {
        // Do nothing
    }

    override fun onActivitySaveInstanceState(
        activity: Activity,
        outState: Bundle,
    ) {
        // Do nothing
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
    }
}
