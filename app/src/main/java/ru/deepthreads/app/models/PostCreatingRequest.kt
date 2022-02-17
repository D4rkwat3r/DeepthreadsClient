package ru.deepthreads.app.models

data class PostCreatingRequest(
    val title: String,
    val content: String,
    val coverResource: String,
    val backgroundResource: String
)
