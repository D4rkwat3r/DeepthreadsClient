package ru.deepthreads.app.models

data class ChatListResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val chatList: List<Chat>
): BaseAPIResponse
