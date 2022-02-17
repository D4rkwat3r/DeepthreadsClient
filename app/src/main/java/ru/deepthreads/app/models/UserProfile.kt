package ru.deepthreads.app.models

data class UserProfile(
    val nickname: String,
    val deepId: String,
    val objectId: String,
    val pictureUrl: String,
    val profileCreatedTime: Long,
    val status: Int
)
