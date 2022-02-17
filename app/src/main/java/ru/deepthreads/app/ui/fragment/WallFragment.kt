package ru.deepthreads.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.PostListAdapter

class WallFragment : Fragment() {
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setup(inflater.inflate(R.layout.fragment_wall, container, false))
    }
    private fun setup(view: View): View {
        val activity = (requireActivity() as AppActivity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.postsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        activity.api.getPosts(0, 20) { response ->
            recyclerView.adapter = PostListAdapter(
                response.postList.toMutableList(),
                activity.api,
                recyclerView
            )
        }
        return view
    }
}