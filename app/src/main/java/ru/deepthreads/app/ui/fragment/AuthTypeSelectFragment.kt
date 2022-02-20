package ru.deepthreads.app.ui.fragment

import android.view.View
import android.widget.Button
import com.google.android.gms.common.SignInButton
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.contract.GAccountSelectingContract
import ru.deepthreads.app.ui.activity.AuthActivity

class AuthTypeSelectFragment : DTFragment<AuthActivity>(R.layout.fragment_auth_type_select) {

    private val contract =  GAccountSelectingContract()
    private val launcher = registerForActivityResult(contract) { gAccount ->
        if (gAccount != null) {
            activity.api.gLogin(gAccount.idToken.toString(), activity::gotoAppActivity)
        }
    }

    override fun onViewCreated(view: View) {
        val login = find<Button>(R.id.loginToAccountButton)
        val register = find<Button>(R.id.createNewAccountButton)
        val google = find<SignInButton>(R.id.googleAuthButton)
        login.setOnClickListener {
            activity.pager2.currentItem = 1
        }
        register.setOnClickListener {
            activity.pager2.currentItem = 2
        }
        google.setOnClickListener {
            launcher.launch(null)
        }
    }
}