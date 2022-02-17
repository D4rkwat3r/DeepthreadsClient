package ru.deepthreads.app.models

data class APIException(
    override val apiStatusCode: Int,
    override val apiMessage: String
): BaseAPIResponse
