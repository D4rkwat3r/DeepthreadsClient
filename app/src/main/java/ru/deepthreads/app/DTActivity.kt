package ru.deepthreads.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import ru.deepthreads.app.ws.SocketWrapper

open class DTActivity : AppCompatActivity() {

    val application: Deepthreads
    get() = Deepthreads.instance
    val api: APIHolder
    get() = application.apiHolder
    val webSocket: SocketWrapper
    get() = application.wsChannel
    val moshi: Moshi
    get() = application.moshi

}