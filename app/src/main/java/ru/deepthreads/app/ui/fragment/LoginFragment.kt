package ru.deepthreads.app.ui.fragment

import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AuthActivity

class LoginFragment : DTFragment<AuthActivity>(R.layout.fragment_login) {
    override fun onViewCreated(view: View) {
        val deepIdInput = find<TextInputEditText>(R.id.loginDeepIdInput)
        val passwordInput = find<TextInputEditText>(R.id.loginPasswordInput)
        val submit = find<Button>(R.id.loginSubmitButton)
        submit.setOnClickListener {
            api.login(deepIdInput.text.toString(), passwordInput.text.toString(), submit, activity::gotoAppActivity)
        }
    }
}