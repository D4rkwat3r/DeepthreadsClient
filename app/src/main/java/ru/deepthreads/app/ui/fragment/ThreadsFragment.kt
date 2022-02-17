package ru.deepthreads.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.deepthreads.app.R

class ThreadsFragment : Fragment() {
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setup(inflater.inflate(R.layout.fragment_threads, container, false))
    }
    private fun setup(view: View): View {
        return view
    }
}