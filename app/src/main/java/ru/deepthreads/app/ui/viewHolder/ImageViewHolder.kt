package ru.deepthreads.app.ui.viewHolder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.deepthreads.app.R
import java.io.File

class ImageViewHolder(itemView: View, private val clickListener: ((Int) -> Unit)?) : RecyclerView.ViewHolder(itemView) {
    private val mainImage: ImageView = itemView.findViewById(R.id.imageSrс)
    init {
        mainImage.setOnClickListener { clickListener?.invoke(adapterPosition) }
    }
    fun load(file: File) {
        val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(20))
        Glide.with(itemView.context)
            .load(file)
            .override(500)
            .apply(requestOptions)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(mainImage)
    }
}