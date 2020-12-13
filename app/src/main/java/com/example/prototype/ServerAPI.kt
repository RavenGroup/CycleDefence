package com.example.prototype


import android.util.Log
import android.widget.TextView

// http requests
//import khttp.get as GET
import khttp.post as POST

// coroutines for asynchronous http requests
import kotlinx.coroutines.*


// Base exception for catching all errors
import java.lang.Exception


class ServerAPI() {
    private val url: String = "https://google.com"

    //    "http://128.75.198.121"
    private suspend fun urlRequest(): String {
        var result: String
        try {
            withContext(Dispatchers.IO) {
                val request = POST(
                    url = "http://128.75.198.121/MobileApp",
                    json = mapOf("id" to "1", "userType" to "mobileApp")
                )
                Log.d(
                    "ServerAPI", request.statusCode.toString()
                )
                Log.d("ServerAPI", request.text)
                result = request.text
            }
        } catch (e: Exception) {
            Log.e("ServerAPI", e.toString())
            result = "ERROR"
        }

        return result
    }


    fun setData(tv: TextView) {
        GlobalScope.launch(Dispatchers.Main) {
            tv.setText(urlRequest())
        }

    }

}