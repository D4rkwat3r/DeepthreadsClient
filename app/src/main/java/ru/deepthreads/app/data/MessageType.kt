package ru.deepthreads.app.data

enum class MessageType(val numerical: Int) {
    NORMAL(0),
    SYSTEM_USER_JOIN(1),
    SYSTEM_USER_LEAVE(2),
    SYSTEM_BROADCAST(3)
}