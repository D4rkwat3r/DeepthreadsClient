package ru.deepthreads.app

import android.app.Application
import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Request
import ru.deepthreads.app.ws.SocketWrapper
import ru.deepthreads.app.utils.DeepthreadsLifecycleCallbacks

class Deepthreads : Application() {

    lateinit var apiHolder: APIHolder
    lateinit var wsChannel: SocketWrapper
    val moshi = Moshi.Builder().build()
    val context: Context
    get() = applicationContext

    override fun onCreate() {
        instance = this
        registerActivityLifecycleCallbacks(DeepthreadsLifecycleCallbacks())
        super.onCreate()
    }

    fun connect() {
        wsChannel = SocketWrapper()
        val request = Request.Builder()
            .url("ws://deepthreads.ru/ws/v1/chat")
            .get()
            .build()
        instance
            .apiHolder
            .getClient()
            .newWebSocket(request, wsChannel)
    }

    companion object {
        @JvmStatic
        lateinit var instance: Deepthreads
    }
}