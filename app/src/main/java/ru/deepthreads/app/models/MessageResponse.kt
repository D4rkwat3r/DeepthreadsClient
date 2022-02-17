package ru.deepthreads.app.models

data class MessageResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val message: Message
): BaseAPIResponse
