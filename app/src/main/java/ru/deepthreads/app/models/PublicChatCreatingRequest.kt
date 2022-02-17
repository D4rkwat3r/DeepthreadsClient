package ru.deepthreads.app.models

data class PublicChatCreatingRequest(
    val title: String,
    val iconResource: String,
    val backgroundResource: String
)