package ru.deepthreads.app.ui.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.deepthreads.app.R

class MessageViewHolder(
    private val itemView: View,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    val messageText: TextView = itemView.findViewById(R.id.messageTextContent)
    private val sendingProgressBar: ProgressBar? = itemView.findViewById(R.id.messageSendingProgressBar)
    private val authorPicture: ImageView? = itemView.findViewById(R.id.messageAuthorAvatar)

    init {
        itemView.setOnClickListener { onClickListener(adapterPosition) }
    }

    fun loadAuthorAvatar(url: String) {
        if (authorPicture != null) {
            Glide.with(itemView.context)
                .load(url)
                .circleCrop()
                .into(authorPicture)
        }
    }

    fun changeState(sent: Boolean) {
        sendingProgressBar?.visibility = if (sent) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}