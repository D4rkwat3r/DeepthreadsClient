package ru.deepthreads.app.models

data class EventLogRequest(
    val activityName: String,
    val activitySimpleName: String,
    val lifecycleCallbackType: String
)
