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

class PostViewHolder(
    itemView: View,
    mainClickListener: (Int) -> Unit,
    onLikeClickListener: (Int) -> Unit,
    onCommentClickListener: (Int) -> Unit
): RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.primaryPostTitle)
    val authorNickname: TextView = itemView.findViewById(R.id.primaryPostAuthorNickname)
    val likesCount: TextView = itemView.findViewById(R.id.primaryPostLikesCount)
    val commentsCount: TextView = itemView.findViewById(R.id.primaryPostCommentsCount)
    private val authorAvatar: ImageView = itemView.findViewById(R.id.primaryPostAuthorAvatar)
    private val cover: ImageView = itemView.findViewById(R.id.primaryPostCover)
    private val likeIcon: ImageView = itemView.findViewById(R.id.primaryPostLikesIcon)
    private val commentIcon: ImageView = itemView.findViewById(R.id.primaryPostCommentsIcon)
    init {
        itemView.setOnClickListener { mainClickListener(adapterPosition) }
        likeIcon.setOnClickListener { onLikeClickListener(adapterPosition) }
        commentIcon.setOnClickListener { onCommentClickListener(adapterPosition) }
    }
    fun loadAuthorAvatar(url: String) {
        Glide.with(itemView.context)
            .load(url)
            .circleCrop()
            .into(authorAvatar)
    }
    fun loadCover(url: String) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(20))
        Glide.with(itemView.context)
            .load(url)
            .apply(requestOptions)
            .into(cover)
    }
}