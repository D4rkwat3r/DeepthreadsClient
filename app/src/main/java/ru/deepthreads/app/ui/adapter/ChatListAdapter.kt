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
import ru.deepthreads.app.models.Chat
import ru.deepthreads.app.utils.PaginatedAdapter
import ru.deepthreads.app.ui.viewHolder.ChatViewHolder

class ChatListAdapter(
    private val activity: Activity,
    private val chats: MutableList<Chat>,
    private val api: APIHolder,
    private val recycler: RecyclerView
): PaginatedAdapter<ChatViewHolder, Chat>(recycler, chats) {
    override fun loadMoreItems(position: Int) {
        api.loadChatList(1, position, position + 30) { response ->
            addItems(response.chatList)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_card, parent, false)
        return ChatViewHolder(itemView, ::resolveClick)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.title.text = chats[position].title
        holder.membersCount.text = chats[position].membersCount.toString()
        holder.loadIcon(chats[position].iconUrl ?: chats[position].owner.pictureUrl)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    private fun resolveClick(position: Int) {
        if (!chats[position].meInChat) {
            AlertDialog.Builder(parent.context)
                .setTitle(parent.context.getString(R.string.not_in_chat))
                .setMessage(parent.context.getString(R.string.join))
                .setPositiveButton(parent.context.getString(R.string.yes)) { dialog, _ ->
                    api.joinChat(chats[position].objectId) {
                        Toast.makeText(parent.context, parent.context.getString(R.string.success), Toast.LENGTH_SHORT).show()
                        chats[position].meInChat = true
                        notifyItemChanged(position)
                        dialog.dismiss()
                        ChatActivity.launch(activity, chats[position].objectId)
                    }
                }
                .setNegativeButton(parent.context.getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            ChatActivity.launch(activity, chats[position].objectId)
        }
    }

}