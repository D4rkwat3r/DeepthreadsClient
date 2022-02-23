package ru.deepthreads.app.ui.fragment

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.MyChatListAdapter


class MyChatsFragment : DTFragment<AppActivity>(R.layout.fragment_my_chats) {
    override fun onViewCreated(view: View) {
        val recyclerView = find<RecyclerView>(R.id.myChatListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        api.loadChatList(2, 0, 20)  { response ->
            val adapter = MyChatListAdapter(activity, response.chatList.toMutableList(), api, recyclerView)
            recyclerView.adapter = adapter
            find<ProgressBar>(R.id.loadingProgressBar).visibility = View.GONE
            listen("add") { data ->
                api.getChat(data.getString("id") ?: return@listen) { response ->
                    adapter.add(response.chat)
                }
            }
        }
    }
}