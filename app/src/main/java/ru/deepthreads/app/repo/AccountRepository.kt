package ru.deepthreads.app.repo

import ru.deepthreads.app.models.Account

object AccountRepository {

    lateinit var account: Account

    fun load(session: Account) {
        account = session
    }

    fun get(): Account? {
        return if (AccountRepository::account.isInitialized) {
            account
        } else {
            null
        }
    }
}