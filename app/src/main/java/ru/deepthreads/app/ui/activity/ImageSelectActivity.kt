package ru.deepthreads.app.ui.activity

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.deepthreads.app.DTActivity
import ru.deepthreads.app.R
import ru.deepthreads.app.ui.adapter.ImageAdapter
import java.io.File

class ImageSelectActivity : DTActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_select)
        val recyclerView = findViewById<RecyclerView>(R.id.imageRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        val dcim = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).absolutePath)
        val adapter = ImageAdapter(mutableListOf())
        adapter.setClickListener { position ->
            Toast.makeText(
                this,
                getString(R.string.uploading_file),
                Toast.LENGTH_SHORT
            ).show()
            api.uploadImage(adapter.files[position]) { response ->
                Toast.makeText(
                    this,
                    getString(R.string.file_uploading_finished),
                    Toast.LENGTH_SHORT
                ).show()
                intent.putExtra("resourceUrl", response.resourceUrl)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
        recyclerView.adapter = adapter
        Thread {
            dcim.walkBottomUp().forEach { file ->
                if (file.extension == "jpg" || file.extension == "jpeg" || file.extension == "png") {
                    runOnUiThread { adapter.add(file) }
                }
            }
        }.start()
    }
}