package ru.deepthreads.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import ru.deepthreads.app.ws.SocketWrapper

open class DTActivity : AppCompatActivity() {

    val application: Deepthreads
    get() = Deepthreads.getInstance()
    val api: APIHolder
    get() = application.getAPI()
    val webSocket: SocketWrapper
    get() = application.getWebSocket()
    val moshi: Moshi
    get() = application.getMoshi()

    override fun onCreate(savedInstanceState: Bundle?) {
        application.apiHolder = APIHolder(this, application.getMoshi())
        super.onCreate(savedInstanceState)
    }
}