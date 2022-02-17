package ru.deepthreads.app.models

data class ChatResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val chat: Chat
): BaseAPIResponse
