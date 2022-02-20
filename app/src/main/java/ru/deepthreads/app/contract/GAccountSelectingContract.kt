package ru.deepthreads.app.contract

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GAccountSelectingContract : ActivityResultContract<Any?, GoogleSignInAccount?>() {

    private val gOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("906788531262-9skp18ap7e2qneou0sdk38lp5cr0r9j9.apps.googleusercontent.com")
        .requestEmail()
        .build()
    private lateinit var gSignClient: GoogleSignInClient

    override fun createIntent(context: Context, input: Any?): Intent {
        gSignClient = GoogleSignIn.getClient(context, gOptions)
        return gSignClient.signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): GoogleSignInAccount? {
        return try {
            GoogleSignIn.getSignedInAccountFromIntent(intent ?: return null).result
        } catch (e: Exception) {
            null
        }
    }
}