package ru.deepthreads.app.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.deepthreads.app.ui.fragment.AuthTypeSelectFragment
import ru.deepthreads.app.ui.fragment.LoginFragment
import ru.deepthreads.app.ui.fragment.RegisterFragment

class AuthPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val auth = AuthTypeSelectFragment()
    private val login = LoginFragment()
    private val register = RegisterFragment()
    private val other = Fragment()

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> auth
            1 -> login
            2 -> register
            else -> other
        }
    }
}