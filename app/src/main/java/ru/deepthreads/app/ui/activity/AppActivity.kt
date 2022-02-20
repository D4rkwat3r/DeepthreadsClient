package ru.deepthreads.app.ui.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.adapter.AppPagerAdapter

class AppActivity : DTActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        val root = findViewById<ConstraintLayout>(R.id.root)
        Glide.with(this)
            .load("http://static.deepthreads.ru/bg2.jpg")
            .into(object: CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    root.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
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