package ru.deepthreads.app.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import ru.deepthreads.app.Deepthreads
import ru.deepthreads.app.repo.AccountRepository

class DeepthreadsLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Deepthreads.getInstance().getAPI().lifecycleCallback(
            p0::class.java.name,
            p0::class.java.simpleName,
            "onActivityCreated",
        )
    }

    override fun onActivityStarted(p0: Activity) {
        Deepthreads.getInstance().getAPI().lifecycleCallback(
            p0::class.java.name,
            p0::class.java.simpleName,
            "onActivityStarted",
        )
    }

    override fun onActivityResumed(p0: Activity) {
        if (AccountRepository.get() != null) {
            try {
                if (!Deepthreads.getInstance().wsChannel.alive()) {
                    Deepthreads.getInstance().connect()
                }
            } catch (e: RuntimeException) {
                Deepthreads.getInstance().connect()
            }
        }
        Deepthreads.getInstance().getAPI().lifecycleCallback(
            p0::class.java.name,
            p0::class.java.simpleName,
            "onActivityResumed",
        )
    }

    override fun onActivityPaused(p0: Activity) {  }

    override fun onActivityStopped(p0: Activity) {  }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {  }

    override fun onActivityDestroyed(p0: Activity) {  }
}