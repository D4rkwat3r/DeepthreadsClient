package ru.deepthreads.app.ws

import okhttp3.WebSocket
import ru.deepthreads.app.DTWebSocket

class EventSocket : DTWebSocket() {
    override fun onReady(channel: WebSocket) {
        // not implemented
    }
}