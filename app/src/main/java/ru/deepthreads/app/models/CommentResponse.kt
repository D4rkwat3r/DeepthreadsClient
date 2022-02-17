package ru.deepthreads.app.models

data class CommentResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val comment: Comment
): BaseAPIResponse
