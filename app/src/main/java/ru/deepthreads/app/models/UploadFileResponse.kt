package ru.deepthreads.app.models

data class UploadFileResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val resourceUrl: String
): BaseAPIResponse
