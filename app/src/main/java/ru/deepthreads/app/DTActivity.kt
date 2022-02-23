package ru.deepthreads.app

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.moshi.Moshi
import ru.deepthreads.app.ws.EventSocket

abstract class DTActivity : AppCompatActivity() {

    val application: Deepthreads
    get() = Deepthreads.instance
    val api: APIHolder
    get() = application.apiHolder
    val eventSocket: EventSocket
    get() = application.wsChannel
    val moshi: Moshi
    get() = application.moshi

    fun loadBackground(url: String) {
        val root = window.decorView.rootView
        Glide.with(this)
            .load(url)
            .into(object: CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    root.background = resource
                }
                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    fun loadImage(url: String, targetId: Int, circleCrop: Boolean = false, roundingOption: Int = 0) {
        var manager = Glide.with(this)
            .load(url)
        if (circleCrop) {
            manager = manager.circleCrop()
        }
        if (roundingOption != 0) {
            manager = manager.apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
        }
        manager.into(findViewById(targetId))
    }

    fun loadImage(sourceId: Int, targetId: Int, circleCrop: Boolean = false, roundingOption: Int = 0) {
        var manager = Glide.with(this)
            .load(sourceId)
        if (circleCrop) {
            manager = manager.circleCrop()
        }
        if (roundingOption != 0) {
            manager = manager.apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
        }
        manager.into(findViewById(targetId))
    }

}