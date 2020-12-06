package com.example.prototype


import android.util.Log

// http requests
import khttp.get as GET
import khttp.post as POST

// coroutines for asynchronous http requests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// Base exception for catching all errors
import java.lang.Exception


//128.75.198.121

class ServerAPI() {
    private val url: String = "https://google.com"

    //    "http://128.75.198.121"
    private suspend fun urlRequest(): String {
        val result: String
        try {
            withContext(Dispatchers.IO) {
                val request = POST(
                    url = "http://128.75.198.121",
                    params = mapOf("userType" to "mobileApp", "id" to "1", "firmware" to "0.1")
                )
                Log.d(
                    "ServerAPI", request.statusCode.toString()
                )
                Log.d("ServerAPI", request.text)

            }
        } catch (e: Exception) {
            Log.e("ServerAPI", e.toString())
        } finally {
            result = "IN DEVELOPMENT"
        }
        return result
    }


    fun getData() {
        GlobalScope.launch(Dispatchers.Main) { urlRequest() }
    }

}