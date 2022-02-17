package ru.deepthreads.app.models

data class Chat(
    val objectId: String,
    val status: Int,
    val createdTime: Long,
    val title: String?,
    val iconUrl: String?,
    val backgroundUrl: String?,
    val type: Int,
    val owner: UserProfile,
    var lastMessage: Message?,
    val membersCount: Int,
    var meInChat: Boolean
)
