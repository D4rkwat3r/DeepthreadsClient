package ru.deepthreads.app.models

data class WSAction <T> (
    val actionType: Int,
    val payload: T? = null
)
