package ru.deepthreads.app.ui.fragment.bottom

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import ru.deepthreads.app.DTBottomDialog
import ru.deepthreads.app.R
import ru.deepthreads.app.contract.ImageSelectingContract
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.ChatListAdapter

class ChatComposeFragment(private val adapter: ChatListAdapter) : DTBottomDialog<AppActivity>(R.layout.fragment_chat_compose) {

    private var chatIconUrl = "http://static.deepthreads.ru/icon.jpg"
    private var chatBackgroundUrl = "http://static.deepthreads.ru/bg3.jpg"
    private val title by lazy { find<TextInputEditText>(R.id.chatTitleInput) }
    private val mIcon by lazy { find<ImageView>(R.id.chatIcon) }
    private val mBackground by lazy { find<ImageView>(R.id.chatBackground) }
    private val submit by lazy { find<Button>(R.id.createChat) }
    private val roundingOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(15))
    private val contract = ImageSelectingContract()
    private val iconSelectingLauncher = registerForActivityResult(contract) { url ->
        if (url != null) {
            chatIconUrl = url
            Glide.with(fragmentView)
                .load(chatIconUrl)
                .circleCrop()
                .into(mIcon)
        }
    }
    private val backgroundSelectingLauncher = registerForActivityResult(contract) { url ->
        if (url != null) {
            chatBackgroundUrl = url
            Glide.with(fragmentView)
                .load(chatIconUrl)
                .apply(roundingOptions)
                .into(mIcon)
        }
    }

    override fun onViewCreated(view: View) {
        Glide.with(view.context)
            .load(chatIconUrl)
            .apply(roundingOptions)
            .into(mBackground)
        Glide.with(view.context)
            .load(chatBackgroundUrl)
            .circleCrop()
            .into(mIcon)
        mIcon.setOnClickListener { iconSelectingLauncher.launch(null) }
        mBackground.setOnClickListener { backgroundSelectingLauncher.launch(null) }
        submit.setOnClickListener {
            if (title.text.toString().isEmpty()) {
                Toast.makeText(activity, "Название не может быть пустым", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            api.startPublicChat(title.text.toString(), chatIconUrl, chatBackgroundUrl) { response ->
                adapter.add(response.chat)
                Toast.makeText(activity, "Успешно", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}