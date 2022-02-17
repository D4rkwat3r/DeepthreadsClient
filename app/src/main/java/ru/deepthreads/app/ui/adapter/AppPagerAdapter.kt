package ru.deepthreads.app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.deepthreads.app.ui.fragment.ThreadsFragment
import ru.deepthreads.app.ui.fragment.ChatsFragment
import ru.deepthreads.app.ui.fragment.MyChatsFragment
import ru.deepthreads.app.ui.fragment.WallFragment

class AppPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val wall = WallFragment()
    private val threads = ThreadsFragment()
    private val chats = ChatsFragment()
    private val myChats = MyChatsFragment()
    private val other = Fragment()

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                wall
            }
            1 -> {
                threads
            }
            2 -> {
                chats
            }
            3 -> {
                myChats
            }
            else -> {
                other
            }
        }
    }

}