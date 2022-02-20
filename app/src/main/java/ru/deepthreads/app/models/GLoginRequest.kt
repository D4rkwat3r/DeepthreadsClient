package ru.deepthreads.app.models

data class GLoginRequest(
    val gToken: String,
    val authTimeMillisecond: Long
)