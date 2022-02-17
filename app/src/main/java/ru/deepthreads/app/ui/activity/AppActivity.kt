package ru.deepthreads.app.ui.activity

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.adapter.AppPagerAdapter

class AppActivity : DTActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        val nav = findViewById<BottomNavigationView>(R.id.mainBottomNavigation)
        val viewPager = findViewById<ViewPager2>(R.id.appViewPager)
        viewPager.adapter = AppPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> nav.selectedItemId = R.id.wall
                    1 -> nav.selectedItemId = R.id.threads
                    2 -> nav.selectedItemId = R.id.chats
                    3 -> nav.selectedItemId = R.id.myChats
                }
            }
        })
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.wall -> viewPager.currentItem = 0
                R.id.threads -> viewPager.currentItem = 1
                R.id.chats -> viewPager.currentItem = 2
                R.id.myChats -> viewPager.currentItem = 3
            }
            true
        }
    }
}