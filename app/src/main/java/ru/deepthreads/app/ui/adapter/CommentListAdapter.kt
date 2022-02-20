package ru.deepthreads.app.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.APIHolder
import ru.deepthreads.app.R
import ru.deepthreads.app.models.Comment
import ru.deepthreads.app.ui.viewHolder.CommentViewHolder
import ru.deepthreads.app.utils.PaginatedAdapter

class CommentListAdapter(
    private val activity: Activity,
    private val parentType: Int,
    private val parentId: String,
    private val comments: MutableList<Comment>,
    private val api: APIHolder,
    private val recycler: RecyclerView
) : PaginatedAdapter<CommentViewHolder, Comment>(recycler, comments) {
    override fun loadMoreItems(position: Int) {
        api.getComments(parentId, parentType, position, position + 30) { response ->
            addItems(response.commentList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.content.text = comments[position].content
        holder.loadAvatar(comments[position].author.pictureUrl)
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}