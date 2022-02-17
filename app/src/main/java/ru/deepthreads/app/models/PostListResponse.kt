package ru.deepthreads.app.models

data class PostListResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val postList: List<Post>
): BaseAPIResponse
