package com.example.prototype

import android.app.Application
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class Requests constructor(context: Context) {

    companion object {
        /*
@Volatile
private var INSTANCE: Requests? = null
fun instance(context: Context) =
    INSTANCE ?: synchronized(this) {
        INSTANCE ?: Requests(context).also {
            INSTANCE = it
        }
    }
*/
        val INSTANCE: Requests by lazy { Requests(Application()) }
//        val INSTANCE: Requests = Requests(Application())
    }

    interface ResponseListener {
        // TODO: 01.01.21  replace any with smt better
        fun onResponse(data: Any) {
            Log.d("Requests/ResponseListener", data::class.toString())
        }

        fun onError(errorData: Any) {
            Log.e("Requests/ResponseListener", errorData.toString())
        }
    }

    fun jsonRequest(url: String, jsonData: JSONObject? = null, listener: ResponseListener) {
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            Response.Listener { response ->
                listener.onResponse(response)
            },
            Response.ErrorListener { error ->
                listener.onError(error)
            }
        )
        requestQueue.add(request)
//        if (requestQueue.){}
        Log.d("Requests/jsonRequest/queue", requestQueue.toString())

    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }


    private fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }


}
