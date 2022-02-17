package ru.deepthreads.app.ui.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.deepthreads.app.R

class ChatViewHolder(private val itemView: View, onClickListener: (Int) -> Unit): RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.chatTitlePreview)
    val membersCount: TextView = itemView.findViewById(R.id.chatMembersCountPreview)
    private val icon = itemView.findViewById<ImageView>(R.id.chatIconPreview)
    init {
        itemView.setOnClickListener { onClickListener(adapterPosition) }
    }
    fun loadIcon(url: String) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(20))
        Glide.with(itemView.context)
            .load(url)
            .apply(requestOptions)
            .into(icon)
    }
}