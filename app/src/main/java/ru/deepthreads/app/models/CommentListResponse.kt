package ru.deepthreads.app.models

data class CommentListResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val commentList: List<Comment>
): BaseAPIResponse
