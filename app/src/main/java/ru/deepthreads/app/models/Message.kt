package ru.deepthreads.app.models

data class Message(
    val objectId: String,
    val status: Int,
    val createdTime: Long,
    val content: String?,
    val type: Int,
    val sender: UserProfile,
    val replyMessage: Message?
)
