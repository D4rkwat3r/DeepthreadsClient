package ru.deepthreads.app.ws

import android.os.Looper
import android.widget.Toast
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import ru.deepthreads.app.Deepthreads
import ru.deepthreads.app.data.WSRecvType
import ru.deepthreads.app.models.WSAction
import ru.deepthreads.app.models.WSEvent
import java.util.*

class SocketWrapper : WebSocketListener() {

    private lateinit var currentSocket: WebSocket
    private val pingResolver = Timer()
    private val moshi = Moshi.Builder().build()
    private val handlers = mutableListOf<(WSEvent) -> Unit>()
    var isAlive = false

    private fun startPingResolver() {
        pingResolver.schedule(object: TimerTask() {
            override fun run() {
                send(WSAction(WSRecvType.PING.numerical, null))
                startPingResolver()
            }
        }, 10000)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        currentSocket = webSocket
        startPingResolver()
        isAlive = true
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        isAlive = false
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        isAlive = false
        webSocket.close(1000, null)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        handlers.forEach { handler ->
            val adapter = moshi.adapter(WSEvent::class.java)
            handler(adapter.fromJson(text) ?: WSEvent(0, null, null))
        }
        super.onMessage(webSocket, text)
    }

    fun addHandler(handler: (WSEvent) -> Unit) {
        handlers.add(handler)
    }

    fun deleteHandler(handler: (WSEvent) -> Unit) {
        handlers.remove(handler)
    }

    fun deleteHandler(handlerPosition: Int) {
        handlers.removeAt(handlerPosition)
    }

    fun close() {
        currentSocket.close(1000, null)
        isAlive = false
    }

    fun send(msg: WSAction) {
        val adapter = moshi.adapter(WSAction::class.java)
        val jsonString = adapter.toJson(msg)
        currentSocket.send(jsonString)
    }
}