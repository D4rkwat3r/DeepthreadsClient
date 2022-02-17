package ru.deepthreads.app.models

data class RegisterRequest(
    val nickname: String,
    val deepId: String,
    val password: String,
    val pictureResource: String
)