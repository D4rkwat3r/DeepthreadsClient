package ru.deepthreads.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.MyChatListAdapter


class MyChatsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setup(inflater.inflate(R.layout.fragment_my_chats, container, false))
    }

    private fun setup(view: View): View {
        val activity = requireActivity() as AppActivity
        val api = activity.api
        val recyclerView = view.findViewById<RecyclerView>(R.id.myChatListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        api.loadChatList(2, 0, 20)  { response ->
            val adapter = MyChatListAdapter(activity, response.chatList.toMutableList(), activity.api, recyclerView)
            recyclerView.adapter = adapter
            view.findViewById<ProgressBar>(R.id.loadingProgressBar).visibility = View.GONE
        }
        return view
    }
}