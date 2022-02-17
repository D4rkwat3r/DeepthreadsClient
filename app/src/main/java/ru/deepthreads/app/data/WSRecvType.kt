package ru.deepthreads.app.data

enum class WSRecvType(val numerical: Int) {
    PING(1),
    MESSAGE(2)
}