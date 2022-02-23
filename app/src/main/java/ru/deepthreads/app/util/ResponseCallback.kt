package ru.deepthreads.app.util

import android.app.Activity
import android.app.AlertDialog
import android.widget.Button
import android.widget.Toast
import com.github.razir.progressbutton.hideProgress
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.deepthreads.app.models.APIException
import ru.deepthreads.app.models.BaseAPIResponse

class ResponseCallback<T : BaseAPIResponse>(
    private val activity: Activity,
    private val moshi: Moshi,
    private val onSuccess: (T) -> Unit,
    private val button: Button? = null,
    private val errorHandler: ((APIException) -> Unit)? = null
) : Callback<T> {

    var text: CharSequence? = button?.text

    override fun onFailure(call: Call<T>, t: Throwable) {
        Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
        button?.hideProgress(text.toString())
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        }
        else if (response.errorBody() != null && response.code() != 101) {
            val exception = moshi.adapter(APIException::class.java).fromJson(response.errorBody()!!.string())!!
            if (errorHandler != null) {
                activity.runOnUiThread { errorHandler.invoke(exception) }
                return
            }
            val alert = AlertDialog.Builder(activity)
            alert.setTitle(exception.apiStatusCode.toString())
            alert.setMessage(exception.apiMessage)
            alert.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            activity.runOnUiThread { alert.show() }
        }
        button?.hideProgress(text.toString())
    }
}