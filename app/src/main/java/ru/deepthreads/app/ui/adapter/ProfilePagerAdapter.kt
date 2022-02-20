package ru.deepthreads.app.ui.adapter

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.deepthreads.app.ui.fragment.CommentsFragment
import ru.deepthreads.app.ui.fragment.WallFragment

class ProfilePagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    userId: String
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val wall = WallFragment()
    private val comments = CommentsFragment()
    private val other = Fragment()

    init {
        comments.arguments = bundleOf("parentId" to userId, "parentType" to 2)
        wall.arguments = bundleOf("isUser" to true, "userId" to userId)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> wall
            1 -> comments
            else -> other
        }
    }

}