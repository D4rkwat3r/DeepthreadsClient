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

    private val moshi = Moshi.Builder().build()

    override fun onCreate() {
        instance = this
        registerActivityLifecycleCallbacks(DeepthreadsLifecycleCallbacks())
        super.onCreate()
    }

    fun getContext(): Context {
        return applicationContext
    }

    fun getAPI(): APIHolder {
        return apiHolder
    }

    fun getWebSocket(): SocketWrapper {
        return wsChannel
    }

    fun getMoshi(): Moshi {
        return moshi
    }

    fun connect() {
        wsChannel = SocketWrapper()
        val request = Request.Builder()
            .url("ws://deepthreads.ru/ws/v1/chat")
            .get()
            .build()
        getInstance()
            .apiHolder
            .getClient()
            .newWebSocket(request, wsChannel)
    }

    companion object {
        @JvmStatic
        private lateinit var instance: Deepthreads
        @JvmStatic
        fun getInstance(): Deepthreads {
            if (!::instance.isInitialized) {
                instance = Deepthreads()
                return instance
            }
            return instance
        }
    }
}