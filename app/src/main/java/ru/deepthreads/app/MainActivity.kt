package ru.deepthreads.app

import android.content.Intent
import android.os.Bundle
import ru.deepthreads.app.ui.activity.AuthActivity

class MainActivity : DTActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, AuthActivity::class.java))
    }
}