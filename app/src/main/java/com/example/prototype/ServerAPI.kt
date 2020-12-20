package com.example.prototype


import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView

// http requests
//import khttp.get as GET
import khttp.post as POST

// coroutines for asynchronous http requests
import kotlinx.coroutines.*
// JSON parser
import org.json.JSONObject


// Base exception for catching all errors
import java.lang.Exception


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
            layout.removeAllViews()
            var tv: TextView
            var layoutHorisontal: LinearLayout
            try {
                val json = JSONObject(urlRequest())
//                /*
                for (i in json.keys()) {
                    layoutHorisontal = LinearLayout(layout.context)
                    layoutHorisontal.orientation = LinearLayout.HORIZONTAL
                    val currentData = json.getJSONObject(i)
                    for (key in currentData.keys()) {
                        tv = TextView(layout.context)
                        tv.setPaddingRelative(20, 0, 0, 10)
                        tv.setText(currentData[key].toString())
                        layoutHorisontal.addView(tv)
                    }


                    layout.addView(layoutHorisontal)
                }
//                */

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
