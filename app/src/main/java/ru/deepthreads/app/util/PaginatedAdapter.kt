package ru.deepthreads.app.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class PaginatedAdapter<VH : RecyclerView.ViewHolder, T>(
    val parent: RecyclerView,
    val items: MutableList<T>
) : RecyclerView.Adapter<VH>() {

    private val layoutManager = parent.layoutManager
    private var isLoading = false
    private var isFinished = false

    init {
        parent.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isLoading || isFinished)
                    return
                val lmLastPosition = when (layoutManager) {
                    is LinearLayoutManager -> {
                        layoutManager.findLastCompletelyVisibleItemPosition()
                    }
                    is StaggeredGridLayoutManager -> {
                        val positions = layoutManager.findLastCompletelyVisibleItemPositions(IntArray(layoutManager.spanCount))
                        positions.maxOrNull() ?: 0
                    }
                    else -> 0
                }
                val adapterLastPosition = if (items.size > 0)
                    items.indexOf(items.last())
                else 0
                if (lmLastPosition == adapterLastPosition
                    || adapterLastPosition - lmLastPosition < 15) {
                        isLoading = true
                    loadMoreItems(adapterLastPosition + 1)
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    fun addItems(elements: List<T>) {
        if (elements.isEmpty()) {
            isLoading = false
            isFinished = true
            return
        }
        elements.forEach { element ->
            items.add(element)
            parent.post {
                notifyItemInserted(items.indexOf(element))
            }
        }
        isLoading = false
    }

    abstract fun loadMoreItems(position: Int)

}