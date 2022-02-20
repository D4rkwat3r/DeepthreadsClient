package ru.deepthreads.app.models

data class UserProfileListResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val userProfileList: List<UserProfile>
): BaseAPIResponse
