package ru.deepthreads.app.models

data class MessageListResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val messageList: List<Message>
): BaseAPIResponse
