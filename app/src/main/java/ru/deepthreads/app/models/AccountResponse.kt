package ru.deepthreads.app.models

data class AccountResponse(
    override val apiStatusCode: Int,
    override val apiMessage: String,
    val account: Account
): BaseAPIResponse
