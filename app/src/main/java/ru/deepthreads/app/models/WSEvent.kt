package ru.deepthreads.app.models

data class WSEvent(
    val eventType: Int,
    val payload: Any?,
    val eventSource: String?
)
