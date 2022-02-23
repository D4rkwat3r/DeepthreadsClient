package ru.deepthreads.app.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.APIHolder
import ru.deepthreads.app.R
import ru.deepthreads.app.data.MessageType
import ru.deepthreads.app.models.Message
import ru.deepthreads.app.repo.RuntimeRepository
import ru.deepthreads.app.ui.activity.MessageActivity
import ru.deepthreads.app.ui.activity.ProfileActivity
import ru.deepthreads.app.util.PaginatedAdapter
import ru.deepthreads.app.ui.viewHolder.MessageViewHolder

class MessageListAdapter(
    private val activity: Activity,
    private val chatId: String,
    val messages: MutableList<Message>,
    private val api: APIHolder,
    private val recycler: RecyclerView
    ) : PaginatedAdapter<MessageViewHolder, Message>(recycler, messages) {
    override fun loadMoreItems(position: Int) {
        api.getMessages(chatId, position, position + 30) { response ->
            addItems(response.messageList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when (viewType) {
            0 -> MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false),
                ::resolveClick,
                ::resolveAvatarClick
            )
            1 -> MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.my_message_item, parent, false),
                ::resolveClick,
                ::resolveAvatarClick
            )
            2 -> MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.system_message_item, parent, false),
                ::resolveClick,
                ::resolveAvatarClick
            )
            else -> MessageViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.unsupported_message_item, parent, false),
                ::resolveClick,
                ::resolveAvatarClick
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageText.text = when {
            messages[position].content != null -> {
                messages[position].content
            }
            messages[position].type == MessageType.SYSTEM_USER_JOIN.numerical -> {
                parent.context.getString(
                    R.string.user_joined_to_chat,
                    messages[position].sender.nickname
                )
            }
            messages[position].type == MessageType.SYSTEM_USER_LEAVE.numerical -> {
                parent.context.getString(
                    R.string.user_left_from_chat,
                    messages[position].sender.nickname
                )
            }
            messages[position].type == MessageType.SYSTEM_BROADCAST.numerical -> {
                parent.context.getString(
                    R.string.administrative_broadcast,
                    messages[position].sender.nickname,
                    messages[position].content
                )
            }
            else -> {
                parent.context.getString(R.string.unsupported_message)
            }
        }
        if (messages[position].objectId == "0") {
            holder.changeState(false)
        } else {
            holder.changeState(true)
        }
        holder.loadAuthorAvatar(messages[position].sender.pictureUrl)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].type == MessageType.NORMAL.numerical) {
            if (messages[position].sender.objectId == RuntimeRepository.account?.userProfile?.objectId) {
                1
            } else {
                0
            }
        } else if (messages[position].type in 1..10) {
            2
        } else {
            1000
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun add(message: Message): Int {
        messages.add(0, message)
        val position = messages.indexOf(message)
        notifyItemInserted(position)
        scrollToLastMessage()
        return position
    }

    fun scrollToLastMessage() {
        parent.scrollToPosition(messages.indexOf(messages.first()))
    }

    fun verify(position: Int, newObject: Message) {
        messages[position] = newObject
        notifyItemChanged(position)
    }

    private fun resolveClick(position: Int) {
        if (messages[position].type == MessageType.NORMAL.numerical) {
            val intent = Intent(activity, MessageActivity::class.java)
            intent.putExtra("chatId", chatId)
            intent.putExtra("messageId", messages[position].objectId)
            activity.startActivity(intent)
        }
    }

    private fun resolveAvatarClick(position: Int) {
        val intent = Intent(activity, ProfileActivity::class.java)
        intent.putExtra("userId", messages[position].sender.objectId)
        activity.startActivity(intent)
    }

}