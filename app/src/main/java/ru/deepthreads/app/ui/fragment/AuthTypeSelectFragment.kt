package ru.deepthreads.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AuthActivity

class AuthTypeSelectFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setup(inflater.inflate(R.layout.fragment_auth_type_select, container, false))
    }
    fun setup(view: View): View {
        val activity = requireActivity() as AuthActivity
        val login = view.findViewById<Button>(R.id.loginToAccountButton)
        val register = view.findViewById<Button>(R.id.createNewAccountButton)
        login.setOnClickListener {
            activity.pager2.currentItem = 1
        }
        register.setOnClickListener {
            activity.pager2.currentItem = 2
        }
        return view
    }
}