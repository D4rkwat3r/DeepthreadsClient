package ru.deepthreads.app.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.models.AccountResponse
import ru.deepthreads.app.repo.RuntimeRepository
import ru.deepthreads.app.ui.adapter.AuthPagerAdapter

class AuthActivity : DTActivity() {

    lateinit var pager2: ViewPager2
    private lateinit var chooserLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val root = findViewById<FrameLayout>(R.id.root)
        Glide.with(this)
            .load("http://static.deepthreads.ru/fog.jpg")
            .into(object: CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    root.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
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

    fun gotoAppActivity(response: AccountResponse) {
        RuntimeRepository.account = response.account
        api.getBlocked { blockedResponse ->
            RuntimeRepository.blocked = blockedResponse.userProfileList
            api.getBlockers { blockersResponse ->
                RuntimeRepository.blockers = blockersResponse.userProfileList
                Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, AppActivity::class.java))
                finish()
            }
        }
    }

}