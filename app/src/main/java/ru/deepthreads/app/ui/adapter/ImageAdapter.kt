package ru.deepthreads.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.viewHolder.ImageViewHolder
import java.io.File

class ImageAdapter(val files: MutableList<File>) : RecyclerView.Adapter<ImageViewHolder>() {

    private var clickListener: ((Int) -> Unit)? = null

    fun setClickListener(listener: (Int) -> Unit) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_card, parent, false)
        return ImageViewHolder(itemView, clickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.load(files[position])
    }

    override fun getItemCount(): Int {
        return files.size
    }

    fun add(file: File) {
        files.add(file)
        notifyItemInserted(files.size - 1)
    }

}