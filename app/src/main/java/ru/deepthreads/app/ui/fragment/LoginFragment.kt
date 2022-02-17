package ru.deepthreads.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AuthActivity
import ru.deepthreads.app.repo.AccountRepository

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setup(inflater.inflate(R.layout.fragment_login, container, false))
    }
    fun setup(view: View): View {
        val activity = requireActivity() as AuthActivity
        val deepIdInput = view.findViewById<TextInputEditText>(R.id.loginDeepIdInput)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.loginPasswordInput)
        val submit = view.findViewById<Button>(R.id.loginSubmitButton)
        submit.setOnClickListener {
            activity.api.login(deepIdInput.text.toString(), passwordInput.text.toString(), submit) { response ->
                Toast.makeText(requireContext(), getString(R.string.success), Toast.LENGTH_SHORT).show()
                AccountRepository.load(response.account)
                activity.gotoNext()
            }
        }
        return view
    }
}