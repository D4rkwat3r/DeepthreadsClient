package ru.deepthreads.app.models


data class Comment(
    val objectId: String,
    val status: Int,
    val createdTime: Long,
    val author: UserProfile,
    val content: String,
    var likeCount: Int,
    var isLikedByMe: Boolean
)
