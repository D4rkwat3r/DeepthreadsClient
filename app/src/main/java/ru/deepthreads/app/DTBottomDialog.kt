package ru.deepthreads.app

import android.app.Dialog
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class DTBottomDialog <T : DTActivity> (private val layoutId: Int) : BottomSheetDialogFragment() {

    lateinit var fragmentView: View
    val activity: T
        get() = requireActivity() as T
    val api: APIHolder
        get() = activity.api

    override fun setupDialog(dialog: Dialog, style: Int) {
        fragmentView = View.inflate(context, layoutId, null)
        onViewCreated(fragmentView)
        dialog.setContentView(fragmentView)
    }

    fun <T : View> find(viewId: Int): T {
        return fragmentView.findViewById(viewId)!!
    }

    abstract fun onViewCreated(view: View)
}