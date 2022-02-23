package ru.deepthreads.app.util

import android.app.Activity
import com.squareup.moshi.JsonAdapter

interface WSEventListener <T> {
    fun onEvent(rawInput: String)
    fun getAllowedCodes(): List<Int>
    fun getJsonAdapter(): JsonAdapter<T>
    fun getParentActivity(): Activity
}