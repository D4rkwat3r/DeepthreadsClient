package ru.deepthreads.app.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.PostListAdapter

class WallFragment : DTFragment<AppActivity>(R.layout.fragment_wall) {
    override fun onViewCreated(view: View) {
        val recyclerView = find<RecyclerView>(R.id.postsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val isUser = requireArguments().getBoolean("isUser")
        if (isUser) {
            api.getUserPosts(
                requireArguments().getString("userId")!!,
                0,
                20
            ) { response ->
                recyclerView.adapter = PostListAdapter(
                    response.postList.toMutableList(),
                    api,
                    recyclerView
                )
            }
        } else {
            api.getPosts(0, 20) { response ->
                recyclerView.adapter = PostListAdapter(
                    response.postList.toMutableList(),
                    api,
                    recyclerView
                )
            }
        }
    }
}