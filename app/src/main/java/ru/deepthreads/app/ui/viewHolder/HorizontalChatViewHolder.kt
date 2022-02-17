package ru.deepthreads.app.ui.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.deepthreads.app.R

class HorizontalChatViewHolder(private val itemView: View, onClickListener: (Int) -> Unit, onLongClickListener: (Int) -> Boolean): RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.horizontalChatTitle)
    val lastMessage: TextView = itemView.findViewById(R.id.chatLastMessagePreview)
    private val icon = itemView.findViewById<ImageView>(R.id.horizontalChatIcon)
    init {
        itemView.setOnClickListener { onClickListener(adapterPosition) }
        itemView.setOnLongClickListener { onLongClickListener(adapterPosition) }
    }
    fun loadIcon(url: String) {
        Glide.with(itemView.context)
            .load(url)
            .circleCrop()
            .into(icon)
    }
}