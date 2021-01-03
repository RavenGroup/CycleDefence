package com.example.prototype

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


object Requests {
    private val queues = mutableMapOf<Context, RequestQueue>()

    fun createRequestQueue(context: Context) {
        if (queues.get(context) == null) {
            queues[context] = Volley.newRequestQueue(context)
        } else {
            Log.d("Requests/createRequestQueue", "Queue is already exists")
        }

        Log.d("Requests/createRequestQueue", "context: $context")
    }

    fun deleteRequestQueue(context: Context) {
        queues[context]!!.cancelAll(context)
    }


    interface ResponseListener {
        fun onResponse(data: JSONObject) {
            Log.d("Requests/ResponseListener", data.toString())
        }

        fun onError(errorData: VolleyError) {
            Log.e("Requests/ResponseListener", errorData.toString())
        }
    }

    fun simpleRequest(
        context: Context,
        url: String,
        id: String,
        listener: ResponseListener
    ) {
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            JSONObject("{\"id\": ${id}}"),
            Response.Listener { response ->
                listener.onResponse(response)
            },
            Response.ErrorListener { error ->
                listener.onError(error)
            }
        )
        queues[context]!!.add(request)
        Log.d("Requests/simpleRequest/queue", queues[context].toString())

    }
    fun jsonRequest(
        context: Context,
        url: String,
        jsonData: JSONObject? = null,
        listener: ResponseListener
    ) {
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
        queues[context]!!.add(request)
//        if (requestQueue.){}
        Log.d("Requests/jsonRequest/queue", queues[context].toString())

    }

}

