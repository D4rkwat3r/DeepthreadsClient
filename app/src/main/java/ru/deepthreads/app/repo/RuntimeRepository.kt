package ru.deepthreads.app.repo

import ru.deepthreads.app.models.Account
import ru.deepthreads.app.models.UserProfile

object RuntimeRepository {

    var account: Account? = null
    var blocked: List<UserProfile>? = null
    var blockers: List<UserProfile>? = null

}