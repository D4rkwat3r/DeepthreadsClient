package ru.deepthreads.app.models

data class UserProfileResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val userProfile: UserProfile
): BaseAPIResponse
