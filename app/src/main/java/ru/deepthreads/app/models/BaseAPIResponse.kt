package ru.deepthreads.app.models

interface BaseAPIResponse {
    val apiStatusCode: Int
    val apiMessage: String
}