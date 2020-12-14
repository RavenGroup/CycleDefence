package com.example.prototype


import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView

// http requests
//import khttp.get as GET
import khttp.post as POST

// coroutines for asynchronous http requests
import kotlinx.coroutines.*
import org.json.JSONObject


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

    fun setUrl(url: String) {
        this.url = url
//        this.url = "https://webhook.site/c3446d65-6779-4917-9726-44bc401dd17d"
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
            Log.e("ServerAPI/urlRequest", e.toString())
            result = e.message.toString()
        }

        return result
    }


    fun setData(layout: LinearLayout) {
        GlobalScope.launch(Dispatchers.Main) {
//            var layout: LinearLayout = LinearLayout(layout.context)
            layout.removeAllViews()
            var tv: TextView
            try {
                val json = JSONObject(urlRequest())
                for (i in json.keys()) {
                    tv = TextView(layout.context)
                    tv.setText(json[i].toString())
                    layout.addView(tv)
                }
            } catch (e: Exception) {
                Log.e("ServerAPI/setData", e.toString())
                tv = TextView(layout.context)
                tv.setText(e.cause.toString())
                layout.addView(tv)
            }


        }

    }

    companion object {
        val instance = ServerAPI()
    }
}
