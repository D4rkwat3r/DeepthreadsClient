package ru.deepthreads.app.models

data class LikeListResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val likeList: List<Like>
): BaseAPIResponse
