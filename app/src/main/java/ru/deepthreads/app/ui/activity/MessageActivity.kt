package ru.deepthreads.app.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.models.Message
import ru.deepthreads.app.repo.RuntimeRepository

class MessageActivity : DTActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        val chatId = intent.extras!!.getString("chatId")!!
        val messageId = intent.extras!!.getString("messageId")!!
        val inflater = LayoutInflater.from(this)
        val root = findViewById<FrameLayout>(R.id.messageActivityRoot)
        api.getMessage(chatId, messageId) { setup(inflater, root, it.message) }
    }

    fun setup(inflater: LayoutInflater, root: FrameLayout, message: Message) {
        val view = if (message.sender.objectId == RuntimeRepository.account?.userProfile?.objectId) {
            inflater.inflate(R.layout.my_message_item, root)
        } else {
            inflater.inflate(R.layout.message_item, root)
        }
        val authorAvatar = view.findViewById<ImageView>(R.id.messageAuthorAvatar)
        val textContent = view.findViewById<TextView>(R.id.messageTextContent)
        Glide.with(this)
            .load(message.sender.pictureUrl)
            .circleCrop()
            .into(authorAvatar)
        textContent.text = message.content
    }
}