package ru.deepthreads.app.models

data class Post(
    val objectId: String,
    val status: Int,
    val createdTime: Long,
    val author: UserProfile,
    val title: String,
    val content: String,
    val coverUrl: String,
    val backgroundUrl: String,
    var likesCount: Int,
    var commentsCount: Int,
    var isLikedByMe: Boolean
)
