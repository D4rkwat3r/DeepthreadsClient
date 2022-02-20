package ru.deepthreads.app.models

data class Account(
    val objectId: String,
    val createdTime: Long,
    val status: Int,
    val nickname: String,
    val deepId: String,
    val password: String?,
    val authToken: String,
    val userProfile: UserProfile
)
