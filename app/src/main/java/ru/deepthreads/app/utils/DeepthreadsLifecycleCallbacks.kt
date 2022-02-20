package ru.deepthreads.app.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import ru.deepthreads.app.APIHolder
import ru.deepthreads.app.Deepthreads
import ru.deepthreads.app.repo.RuntimeRepository

class DeepthreadsLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        Deepthreads.instance.apiHolder = APIHolder(p0, Deepthreads.instance.moshi)
        Deepthreads.instance.apiHolder.lifecycleCallback(
            p0::class.java.name,
            p0::class.java.simpleName,
            "onActivityCreated",
        )
    }

    override fun onActivityStarted(p0: Activity) {
        Deepthreads.instance.apiHolder = APIHolder(p0, Deepthreads.instance.moshi)
        Deepthreads.instance.apiHolder.lifecycleCallback(
            p0::class.java.name,
            p0::class.java.simpleName,
            "onActivityStarted",
        )
    }

    override fun onActivityResumed(p0: Activity) {
        Deepthreads.instance.apiHolder = APIHolder(p0, Deepthreads.instance.moshi)
        if (RuntimeRepository.account != null) {
            try {
                if (!Deepthreads.instance.wsChannel.isAlive) {
                    Deepthreads.instance.connect()
                }
            } catch (e: RuntimeException) {
                Deepthreads.instance.connect()
            }
        }
        Deepthreads.instance.apiHolder.lifecycleCallback(
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