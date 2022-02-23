package ru.deepthreads.app

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import ru.deepthreads.app.data.WSRecvType
import ru.deepthreads.app.models.WSAction
import ru.deepthreads.app.models.WSEvent
import ru.deepthreads.app.util.WSEventListener
import java.util.*

abstract class DTWebSocket : WebSocketListener() {

    private lateinit var ws: WebSocket
    private val moshi: Moshi
    get() = Deepthreads.instance.moshi
    var alive = false
    private val listeners = mutableListOf<WSEventListener<*>>()

    private fun pingCycle() {
        Timer().schedule(object: TimerTask() {
            override fun run() {
                send(WSAction(WSRecvType.PING.numerical))
                pingCycle()
            }
        }, 10000)
    }

    fun connect(client: OkHttpClient, endpoint: String): DTWebSocket {
        val request = Request.Builder()
            .get()
            .url("http://deepthreads.ru/ws/v1/$endpoint")
            .build()
        client.newWebSocket(request, this)
        return this
    }

    fun send(msg: WSAction<Any>) {
        val adapter = moshi.adapter<WSAction<Any>>(Types.newParameterizedType(WSAction::class.java, Any()::class.java))
        val jsonString = adapter.toJson(msg)
        ws.send(jsonString)
    }

    fun <T> send(msg: WSAction<T>, payloadClass: Class<T>) {
        val adapter = moshi.adapter<WSAction<T>>(Types.newParameterizedType(WSAction::class.java, payloadClass))
        val jsonString = adapter.toJson(msg)
        ws.send(jsonString)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        ws = webSocket
        pingCycle()
        alive = true
        onReady(ws)
        super.onOpen(webSocket, response)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        alive = false
        super.onFailure(webSocket, t, response)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        alive = false
        super.onClosed(webSocket, code, reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        val code = getEventType(text)
        listeners.forEach { listener ->
            if (listener.getAllowedCodes().contains(code)) {
                listener.onEvent(text)
            }
        }
        super.onMessage(webSocket, text)
    }

    fun <T> registerListener(activity: Activity, allowedCode: Int, adapter: JsonAdapter<T>, handler: (T) -> Unit) {
        listeners += object: WSEventListener<T> {
            override fun onEvent(rawInput: String) {
                getParentActivity().runOnUiThread {
                    handler(getJsonAdapter().fromJson(rawInput) ?: return@runOnUiThread)
                }
            }
            override fun getAllowedCodes(): List<Int> {
                return listOf(allowedCode)
            }
            override fun getJsonAdapter(): JsonAdapter<T> {
                return adapter
            }
            override fun getParentActivity(): Activity {
                return activity
            }
        }
    }

    fun <T> registerListener(activity: Activity, allowedCodes: List<Int>, adapter: JsonAdapter<T>, handler: (T?) -> Unit) {
        listeners += object: WSEventListener<T> {
            override fun onEvent(rawInput: String) {
                getParentActivity().runOnUiThread {
                    handler(getJsonAdapter().fromJson(rawInput) ?: return@runOnUiThread)
                }
            }
            override fun getAllowedCodes(): List<Int> {
                return allowedCodes
            }
            override fun getJsonAdapter(): JsonAdapter<T> {
                return adapter
            }

            override fun getParentActivity(): Activity {
                return activity
            }
        }
    }

    private fun getEventType(rawInput: String): Int {
        val adapter = moshi.adapter<WSEvent<Any>>(Types.newParameterizedType(WSEvent::class.java, Any()::class.java))
        val obj = adapter.fromJson(rawInput)
        return obj!!.eventType
    }

    fun clearAllActivityListeners(activity: Activity) {
        val targetedListeners = mutableListOf<WSEventListener<*>>()
        targetedListeners.addAll(listeners.filter { it.getParentActivity() == activity })
        targetedListeners.forEach { listeners.remove(it) }
    }

    abstract fun onReady(channel: WebSocket)
}