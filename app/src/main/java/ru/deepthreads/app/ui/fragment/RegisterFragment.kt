package ru.deepthreads.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AuthActivity
import ru.deepthreads.app.contract.ImageSelectingContract
import ru.deepthreads.app.repo.AccountRepository

class RegisterFragment : Fragment() {

    private lateinit var profilePicturePreview: ImageView
    private lateinit var mView: View
    private var imageUrl: String? = null
    private val contract = ImageSelectingContract()
    private val chooser = registerForActivityResult(contract) { url ->
        if (url == null)
            return@registerForActivityResult
        imageUrl = url
        Glide.with(mView)
            .load(imageUrl)
            .circleCrop()
            .into(profilePicturePreview)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setup(inflater.inflate(R.layout.fragment_register, container, false))
    }
    fun setup(view: View): View {
        mView = view
        val activity = requireActivity() as AuthActivity
        profilePicturePreview = view.findViewById(R.id.registerProfilePicturePreview)
        Glide.with(view)
            .load(R.drawable.empty_profile_picture)
            .circleCrop()
            .into(profilePicturePreview)
        profilePicturePreview.setOnClickListener {
            chooser.launch(null)
        }
        val nicknameInput = view.findViewById<TextInputEditText>(R.id.registerNicknameInput)
        val deepIdInput = view.findViewById<TextInputEditText>(R.id.registerDeepIdInput)
        val passwordInput = view.findViewById<TextInputEditText>(R.id.registerPasswordInput)
        val submit = view.findViewById<Button>(R.id.registerSubmitButton)
        submit.setOnClickListener {
            activity.api.register(
                nicknameInput.text.toString(),
                deepIdInput.text.toString(),
                passwordInput.text.toString(),
                imageUrl.toString(),
                submit
            ) { response ->
                Toast.makeText(activity, getString(R.string.success), Toast.LENGTH_SHORT).show()
                AccountRepository.load(response.account)
                activity.gotoNext()
            }
        }
        return view
    }
}