package ru.deepthreads.app.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_profile.*
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.models.UserProfileResponse
import ru.deepthreads.app.repo.RuntimeRepository
import ru.deepthreads.app.ui.adapter.ProfilePagerAdapter

class ProfileActivity : DTActivity() {

    private val picture by lazy { findViewById<ImageView>(R.id.profilePicture) }
    private val nickname by lazy { findViewById<TextView>(R.id.userNickname) }
    private val subscribersCount by lazy { findViewById<TextView>(R.id.subscribersCount) }
    private val options = RequestOptions().transform(CenterCrop(), RoundedCorners(20))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val userId = intent.extras!!.getString("userId")!!
        Glide.with(this)
            .load(R.drawable.empty_profile_picture)
            .apply(options)
            .into(picture)
        if (userId == RuntimeRepository.account?.userProfile?.objectId) {
            api.getMe(::setupUser)
        } else {
            api.getUser(userId, ::setupUser)
        }
    }
    private fun setupUser(response: UserProfileResponse) {
        val pager2 = findViewById<ViewPager2>(R.id.viewPager2)
        val tabLayout = findViewById<TabLayout>(R.id.profileTabs)
        val adapter = ProfilePagerAdapter(supportFragmentManager, lifecycle, response.userProfile.objectId)
        pager2.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.publications)
                1 -> tab.text = getString(R.string.comments)
            }
        }.attach()
        nickname.text = response.userProfile.nickname
        subscribersCount.text = getString(R.string.subscribers_count, response.userProfile.subscribersCount)
        Glide.with(this)
            .load(response.userProfile.pictureUrl)
            .apply(options)
            .into(picture)
    }
}