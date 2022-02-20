package ru.deepthreads.app.models

data class UserProfile(
    val objectId: String,
    val createdTime: Long,
    val status: Int,
    val nickname: String,
    val deepId: String,
    val pictureUrl: String,
    val subscribersCount: Int,
    val commentsCount: Int,
    val role: Int
)
