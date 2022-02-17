package ru.deepthreads.app.models

data class Account(
    val nickname: String,
    val deepId: String,
    val password: String,
    val authToken: String,
    val createdTime: Long,
    val userProfile: UserProfile
)
