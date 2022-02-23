package ru.deepthreads.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.APIHolder
import ru.deepthreads.app.R
import ru.deepthreads.app.models.Post
import ru.deepthreads.app.util.PaginatedAdapter
import ru.deepthreads.app.ui.viewHolder.PostViewHolder

class PostListAdapter(
    private val posts: MutableList<Post>,
    private val api: APIHolder,
    private val recycler: RecyclerView
    ) : PaginatedAdapter<PostViewHolder, Post>(recycler, posts) {

    override fun loadMoreItems(position: Int) {
        api.getPosts(position, position + 30) { response ->
            addItems(response.postList)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.post_card, parent, false)
        return PostViewHolder(
            itemView,
            {},
            ::resolveLike,
            {}
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.title.text = posts[position].title
        holder.authorNickname.text = posts[position].author.nickname
        holder.likesCount.text = posts[position].likesCount.toString()
        holder.commentsCount.text = posts[position].commentsCount.toString()
        holder.loadAuthorAvatar(posts[position].author.pictureUrl)
        holder.loadCover(posts[position].coverUrl)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    private fun resolveLike(position: Int) {
        if (!posts[position].isLikedByMe) {
            api.likePost(posts[position].objectId) {
                posts[position].likesCount++
                posts[position].isLikedByMe = true
                notifyItemChanged(position)
            }
        }
        else {
            api.unlikePost(posts[position].objectId) {
                posts[position].likesCount--
                posts[position].isLikedByMe = false
                notifyItemChanged(position)
            }
        }
    }
}