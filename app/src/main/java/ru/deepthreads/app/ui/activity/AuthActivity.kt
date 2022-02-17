package ru.deepthreads.app.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.viewpager2.widget.ViewPager2
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.adapter.AuthPagerAdapter

class AuthActivity : DTActivity() {

    lateinit var pager2: ViewPager2
    private lateinit var chooserLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        pager2 = findViewById(R.id.authViewPager)
        pager2.adapter = AuthPagerAdapter(supportFragmentManager, lifecycle)
        pager2.isUserInputEnabled = false
    }

    override fun onBackPressed() {
        when (pager2.currentItem) {
            0 -> super.onBackPressed()
            else -> pager2.currentItem = pager2.currentItem - 1
        }
    }

    fun gotoNext() {
        startActivity(Intent(this, AppActivity::class.java))
    }

}