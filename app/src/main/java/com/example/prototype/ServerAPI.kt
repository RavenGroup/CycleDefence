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


class ServerAPI {
    private var url: String = "http://128.75.198.121/MobileApp"
    private var id: String = "4"
    var places = emptyMap<String, Map<String, String>>().toMutableMap()


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
        drawMap()
        layout.removeAllViews()
        var tv: TextView
        var layoutHorisontal: LinearLayout
        try {
            for (i in places.keys) {
                layoutHorisontal = LinearLayout(layout.context)
                layoutHorisontal.orientation = LinearLayout.HORIZONTAL
                val currentData = places[i]
                if (currentData != null) {
                    for (key in currentData.keys) {
                        tv = TextView(layout.context)
                        tv.setPaddingRelative(20, 0, 0, 10)
                        tv.text = currentData[key].toString()
                        layoutHorisontal.addView(tv)
                    }
                }


                layout.addView(layoutHorisontal)
            }
//                */

        } catch (e: Exception) {
            Log.e("ServerAPI/setData", e.toString())
            tv = TextView(layout.context)
            tv.text = e.cause.toString()
            layout.addView(tv)
        }


    }

    fun drawMap() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val json = JSONObject(urlRequest())
                var place: JSONObject
                for (i in json.keys()) {
                    place = json.getJSONObject(i)
                    places[i] = mapOf(
                        "time" to place["time"].toString(),
                        "lat" to place["lat"].toString(),
                        "long" to place["long"].toString()
                    )
                }
                Log.d("ServerAPI/drawMap", "$id $places")
            } catch (e: Exception) {
                places.clear()
                places["1"] = mapOf("error" to "ERROR")
                Log.e("ServerAPI/drawMap", e.toString())
            }


        }
    }


    companion object {
        val instance = ServerAPI()
    }

}
