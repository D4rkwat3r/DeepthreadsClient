package ru.deepthreads.app.ui.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.deepthreads.app.R

class CommentViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    val content = itemView.findViewById<TextView>(R.id.commentTextContent)
    private val authorAvatar = itemView.findViewById<ImageView>(R.id.authorAvatar)
    fun loadAvatar(url: String) {
        Glide.with(itemView.context)
            .load(url)
            .circleCrop()
            .into(authorAvatar)
    }
}