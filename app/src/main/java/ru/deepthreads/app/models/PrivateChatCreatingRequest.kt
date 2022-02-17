package ru.deepthreads.app.models

data class PrivateChatCreatingRequest(
    val invitedUsersIds: List<String>,
    val initialMessage: String
)
