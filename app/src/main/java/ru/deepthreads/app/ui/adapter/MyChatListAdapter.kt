package ru.deepthreads.app.ui.adapter

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.APIHolder
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.ChatActivity
import ru.deepthreads.app.data.MessageType
import ru.deepthreads.app.models.Chat
import ru.deepthreads.app.utils.PaginatedAdapter
import ru.deepthreads.app.ui.viewHolder.HorizontalChatViewHolder

class MyChatListAdapter(
    private val activity: Activity,
    private val chats: MutableList<Chat>,
    private val api: APIHolder,
    private val recycler: RecyclerView
) : PaginatedAdapter<HorizontalChatViewHolder, Chat>(recycler, chats) {

    override fun loadMoreItems(position: Int) {
        api.loadChatList(2, position, position + 30) { response ->
            addItems(response.chatList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalChatViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_card_horizontal, parent, false)
        return HorizontalChatViewHolder(itemView, ::resolveClick, ::resolveLongClick)
    }

    override fun onBindViewHolder(holder: HorizontalChatViewHolder, position: Int) {
        holder.title.text = chats[position].title
        holder.lastMessage.text = if (chats[position].lastMessage != null) {
            when (chats[position].lastMessage?.type) {
                MessageType.NORMAL.numerical -> {
                    parent.context
                        .getString(
                            R.string.last_message,
                            chats[position].lastMessage?.sender?.nickname,
                            chats[position].lastMessage?.content
                        )
                }
                MessageType.SYSTEM_USER_JOIN.numerical -> {
                    parent.context.getString(
                        R.string.user_joined_to_chat,
                        chats[position].lastMessage?.sender?.nickname
                    )
                }
                MessageType.SYSTEM_USER_LEAVE.numerical -> {
                    parent.context.getString(
                        R.string.user_left_from_chat,
                        chats[position].lastMessage?.sender?.nickname
                    )
                }
                MessageType.SYSTEM_BROADCAST.numerical -> {
                    parent.context.getString(
                        R.string.administrative_broadcast,
                        chats[position].lastMessage?.sender?.nickname,
                        chats[position].lastMessage?.content
                    )
                }
                else -> parent.context.getString(R.string.unsupported_message)
            }
        } else {
            "Сообщений нет"
        }
        holder.loadIcon(chats[position].iconUrl ?: chats[position].owner.pictureUrl)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun resolveClick(position: Int) {
        ChatActivity.launch(activity, chats[position].objectId)
    }

    private fun resolveLongClick(position: Int): Boolean {
        AlertDialog.Builder(parent.context)
            .setTitle(parent.context.getString(R.string.leave_chat))
            .setMessage(parent.context.getString(R.string.submit_leave_chat_intent))
            .setPositiveButton(parent.context.getString(R.string.yes)) { dialog, _ ->
                api.leaveChat(chats[position].objectId) {
                    Toast.makeText(parent.context, parent.context.getString(R.string.success), Toast.LENGTH_SHORT).show()
                    chats.removeAt(position)
                    notifyItemRemoved(position)
                    dialog.dismiss()
                }
            }
            .setNegativeButton(parent.context.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
        return true
    }
}