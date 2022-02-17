package ru.deepthreads.app.models

data class Like(
    val objectId: String,
    val status: Int,
    val createdTime: Long,
    val author: UserProfile
)
