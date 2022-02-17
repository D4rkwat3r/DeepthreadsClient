package ru.deepthreads.app.models

data class PostResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val post: Post
): BaseAPIResponse
