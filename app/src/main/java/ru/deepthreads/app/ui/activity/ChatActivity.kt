package ru.deepthreads.app.ui.activity

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.adapter.MessageListAdapter
import ru.deepthreads.app.models.Chat
import ru.deepthreads.app.models.Message
import ru.deepthreads.app.repo.AccountRepository

class ChatActivity : DTActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val chatId = intent.extras!!.getString("chatId")!!
        api.getChat(chatId) { chatResponse ->
            api.getMessages(chatResponse.chat.objectId, 0, 20) { messagesResponse ->
                setupView(chatResponse.chat, messagesResponse.messageList)
            }
        }
    }

    fun setupView(chat: Chat, messages: List<Message>) {
        val root = findViewById<ConstraintLayout>(R.id.chatActivityLayoutRoot)
        Glide.with(applicationContext)
            .load(chat.backgroundUrl)
            .into(object: CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    root.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
        val recyclerView = findViewById<RecyclerView>(R.id.chatMessagesRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        val inputLayout = findViewById<TextInputLayout>(R.id.messageInputLayout)
        val inputField = findViewById<TextInputEditText>(R.id.chatMessageInputField)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager
        val adapter = MessageListAdapter(
            this,
            chat.objectId,
            messages.toMutableList(),
            api,
            recyclerView
        )
        recyclerView.adapter = adapter
        inputLayout.setEndIconOnClickListener {
            val textContent = inputField.text.toString()
            if (textContent.isEmpty())
                return@setEndIconOnClickListener
            inputField.setText(String())
            messageSendingAttempt(adapter, chat.objectId, textContent)
        }
        inputLayout.setOnClickListener { adapter.scrollToLastMessage() }
        if (adapter.messages.isNotEmpty())
            adapter.scrollToLastMessage()
        findViewById<ProgressBar>(R.id.loadingProgressBar).visibility = View.GONE
    }

    fun messageSendingAttempt(adapter: MessageListAdapter, chatId: String, content: String) {
        val pseudoMessage = Message(
            "0",
            0,
            0,
            content,
            0,
            AccountRepository.get()!!.userProfile,
            null
        )
        val position = adapter.add(pseudoMessage)
        api.sendMessage(chatId, content, 0, { messageSendingAttempt(adapter, chatId, content) } ) { response ->
            adapter.verify(position, response.message)
        }
    }

    companion object {
        @JvmStatic
        fun launch(activity: Activity, chatId: String) {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra("chatId", chatId)
            activity.startActivity(intent)
        }
    }
}