package ru.deepthreads.app.models

data class WSEvent <T> (
    val eventType: Int,
    val payload: T?,
    val eventSource: String?
)
