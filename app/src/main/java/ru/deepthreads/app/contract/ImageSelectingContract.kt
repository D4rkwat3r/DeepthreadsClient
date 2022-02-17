package ru.deepthreads.app.contract

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import ru.deepthreads.app.ui.activity.ImageSelectActivity

class ImageSelectingContract : ActivityResultContract<Any?, String?>() {
    override fun createIntent(context: Context, input: Any?): Intent {
        return Intent(context, ImageSelectActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode != Activity.RESULT_OK) return null
        return intent!!.getStringExtra("resourceUrl")
    }

}