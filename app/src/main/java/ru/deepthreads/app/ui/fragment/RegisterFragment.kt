package ru.deepthreads.app.ui.fragment

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.squareup.moshi.Moshi
import ru.deepthreads.app.APIHolder
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AuthActivity
import ru.deepthreads.app.contract.ImageSelectingContract
import ru.deepthreads.app.repo.RuntimeRepository

class RegisterFragment : DTFragment<AuthActivity>(R.layout.fragment_register) {

    private val profilePicturePreview by lazy { find<ImageView>(R.id.registerProfilePicturePreview) }
    private var imageUrl = "http://static.deepthreads.ru/profile.jpg"
    private val contract = ImageSelectingContract()
    private val chooser = registerForActivityResult(contract) { url ->
        if (url == null)
            return@registerForActivityResult
        imageUrl = url
        Glide.with(fragmentView)
            .load(imageUrl)
            .circleCrop()
            .into(profilePicturePreview)
    }

    override fun onViewCreated(view: View) {
        Glide.with(view)
            .load(R.drawable.empty_profile_picture)
            .circleCrop()
            .into(profilePicturePreview)
        profilePicturePreview.setOnClickListener {
            chooser.launch(null)
        }
        val nicknameInput = find<TextInputEditText>(R.id.registerNicknameInput)
        val deepIdInput = find<TextInputEditText>(R.id.registerDeepIdInput)
        val passwordInput = find<TextInputEditText>(R.id.registerPasswordInput)
        val submit = find<Button>(R.id.registerSubmitButton)
        submit.setOnClickListener {
            api.register(
                nicknameInput.text.toString(),
                deepIdInput.text.toString(),
                passwordInput.text.toString(),
                imageUrl,
                submit,
                activity::gotoAppActivity)
        }
    }
}