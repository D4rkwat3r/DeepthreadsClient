package ru.deepthreads.app

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import ru.deepthreads.app.ws.EventSocket
import ru.deepthreads.app.util.DeepthreadsLifecycleCallbacks

class Deepthreads : Application() {

    lateinit var apiHolder: APIHolder
    lateinit var wsChannel: EventSocket
    val moshi = Moshi.Builder().build()
    val context: Context
    get() = applicationContext

    override fun onCreate() {
        instance = this
        registerActivityLifecycleCallbacks(DeepthreadsLifecycleCallbacks())
        super.onCreate()
    }

    fun connect() {
        if (!::wsChannel.isInitialized) {
            wsChannel = EventSocket()
        }
        wsChannel.connect(apiHolder.client, "chat")
    }

    companion object {
        @JvmStatic
        lateinit var instance: Deepthreads
    }
}