package ru.deepthreads.app.ui.fragment

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.deepthreads.app.DTFragment
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.activity.AppActivity
import ru.deepthreads.app.ui.adapter.ChatListAdapter
import ru.deepthreads.app.ui.fragment.bottom.ChatComposeFragment

class ChatsFragment : DTFragment<AppActivity>(R.layout.fragment_chats) {
    override fun onViewCreated(view: View) {
        val recyclerView = find<RecyclerView>(R.id.chatsRecyclerView)
        val fab = find<FloatingActionButton>(R.id.createChatFab)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        api.loadChatList(1, 0, 20) { response ->
            val adapter = ChatListAdapter(
                this,
                activity,
                response.chatList.toMutableList(),
                api,
                recyclerView
            )
            recyclerView.adapter = adapter
            find<ProgressBar>(R.id.loadingProgressBar).visibility = View.GONE
            fab.setOnClickListener {
                val dialog = ChatComposeFragment(adapter)
                dialog.show(parentFragmentManager, "ChatComposeFragment")
            }
        }
    }
}