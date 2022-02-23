package ru.deepthreads.app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.moshi.Types
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.data.WSRecvType
import ru.deepthreads.app.ui.adapter.MessageListAdapter
import ru.deepthreads.app.models.Chat
import ru.deepthreads.app.models.Message
import ru.deepthreads.app.models.WSEvent
import ru.deepthreads.app.repo.RuntimeRepository

class ChatActivity : DTActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val chatId = intent.extras!!.getString("chatId")!!
        val toolBar = findViewById<Toolbar>(R.id.toolBar)
        api.getChat(chatId) { chatResponse ->
            toolBar.title = chatResponse.chat.title
            api.getMessages(chatResponse.chat.objectId, 0, 20) { messagesResponse ->
                setupChat(chatResponse.chat, messagesResponse.messageList)
            }
        }
    }

    fun setupChat(chat: Chat, messages: List<Message>) {
        loadBackground(chat.backgroundUrl ?: "http://static.deepthreads.ru/cbg.jpg")
        val recyclerView = findViewById<RecyclerView>(R.id.chatMessagesRecyclerView)
        val inputLayout = findViewById<TextInputLayout>(R.id.messageInputLayout)
        val inputField = findViewById<TextInputEditText>(R.id.chatMessageInputField)
        val adapter = setupRecyclerView(chat, messages, recyclerView)
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
        eventSocket.registerListener(
            this,
            WSRecvType.MESSAGE.numerical,
            moshi.adapter<WSEvent<Message>>(Types.newParameterizedType(WSEvent::class.java, Message::class.java))
        ) { event ->
            if (event.payload?.sender?.objectId != RuntimeRepository.account?.userProfile?.objectId
                && event.eventSource == chat.objectId) {
                adapter.add(event.payload ?: return@registerListener)
            }
        }
    }

    fun setupRecyclerView(chat: Chat, messages: List<Message>, recyclerView: RecyclerView): MessageListAdapter {
        val layoutManager = LinearLayoutManager(this)
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
        return adapter
    }

    fun messageSendingAttempt(adapter: MessageListAdapter, chatId: String, content: String) {
        val pseudoMessage = Message(
            "0",
            0,
            0,
            content,
            0,
            RuntimeRepository.account!!.userProfile,
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