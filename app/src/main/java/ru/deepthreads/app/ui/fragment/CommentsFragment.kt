package ru.deepthreads.app.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.models.CommentListResponse
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.CommentListAdapter

class CommentsFragment : DTFragment<AppActivity>(R.layout.fragment_comments) {

    private val recyclerView by lazy { find<RecyclerView>(R.id.commentsRecyclerView) }
    private val parentId by lazy { requireArguments().getString("parentId")!! }
    private val parentType by lazy { requireArguments().getInt("parentType") }

    override fun onViewCreated(view: View) {
        api.getComments(parentId, parentType, 0, 30, ::setupRecyclerView)
    }

    private fun setupRecyclerView(response: CommentListResponse) {
        val adapter = CommentListAdapter(
            activity,
            parentType,
            parentId,
            response.commentList.toMutableList(),
            api,
            recyclerView
        )
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }
}