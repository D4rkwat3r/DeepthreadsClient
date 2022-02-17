package ru.deepthreads.app.models

data class EmptyOKResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String
): BaseAPIResponse
