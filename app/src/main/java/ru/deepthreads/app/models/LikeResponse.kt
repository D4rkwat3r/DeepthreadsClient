package ru.deepthreads.app.models

data class LikeResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val like: Like
): BaseAPIResponse
