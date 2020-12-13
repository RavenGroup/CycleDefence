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
import kotlin.reflect.typeOf


class ServerAPI() {
    private var url: String = "http://128.75.198.121/MobileApp"
    private var id: String = "1"

    // TODO: 13.12.20 rework or delete this in production
    fun setId(id: String) {
        this.id = id
    }
    fun setUrl(url:String){
        this.url = "http://$url/MobileApp"
    }
    // end of delete

    private suspend fun urlRequest(): String {
        var result: String
        try {
            withContext(Dispatchers.IO) {
                val request = POST(
                        url = url,
                        json = mapOf("id" to id)
                )
                Log.d(
                        "ServerAPI", request.statusCode.toString()
                )
                Log.d("ServerAPI", request.text)
                result = request.text
            }
        } catch (e: Exception) {
            Log.e("ServerAPI", e.toString())
            result = e.message.toString()
        }

        return result
    }


    fun setData(tv: TextView) {
        GlobalScope.launch(Dispatchers.Main) {
            tv.setText(urlRequest())
        }

    }

    companion object {
        val instance = ServerAPI()
    }
}
