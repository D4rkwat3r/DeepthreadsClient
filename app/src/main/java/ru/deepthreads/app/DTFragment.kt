package ru.deepthreads.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

abstract class DTFragment <T : DTActivity> (private val layoutId: Int) : Fragment() {

    lateinit var fragmentView: View
    val activity: T
    get() = requireActivity() as T
    val supportFragmentManager: FragmentManager
    get() = activity.supportFragmentManager
    val api: APIHolder
    get() = activity.api


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(layoutId, container, false)
        onViewCreated(fragmentView)
        return fragmentView
    }

    fun <T : View> find(viewId: Int): T {
        return fragmentView.findViewById(viewId)!!
    }

    fun dismiss() {
        activity.supportFragmentManager.popBackStack()
    }

    fun listen(key: String, handler: (Bundle) -> Unit) {
        supportFragmentManager.setFragmentResultListener(key, activity) { _, data -> handler(data) }
    }

    fun broadcast(key: String, data: Bundle) {
        supportFragmentManager.setFragmentResult(key, data)
    }

    abstract fun onViewCreated(view: View)
}