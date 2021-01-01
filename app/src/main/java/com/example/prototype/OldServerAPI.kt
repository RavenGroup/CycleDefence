package com.example.prototype


// khttp requests

// coroutines for asynchronous http requests
// JSON parser


// Base exception for catching all errors
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import khttp.post as POST


class OldServerAPI {
    private var url: String = "http://128.75.198.121"
    private var id: String = "4"
    var data = emptyMap<String, Map<String, String>>().toMutableMap()


    // TODO: 13.12.20 rework or delete this in production
    fun setId(id: String) {
        this.id = id
    }

    fun setUrl(url: String) {
        this.url = url
    }
    // end of delete

    private suspend fun urlRequest(path: String = ""): String {
        var result: String
        try {
            withContext(Dispatchers.IO) {
                val request = POST(
                    url = "$url/$path",
                    json = mapOf("id" to id)
                )
                Log.d("OldServerAPI/url/status", request.statusCode.toString())
                result = request.text

            }
        } catch (e: Exception) {
            Log.e("OldServerAPI/urlRequest", e.toString())
            result = e.message.toString()
        }

        return result
    }

    fun drawMap() {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val json = JSONObject(urlRequest("MobileApp"))
                var current: JSONObject
                for (i in json.keys()) {
                    current = json.getJSONObject(i)
                    data[i] = mapOf(
                        "date" to current["date"].toString(),
                        "time" to current["time"].toString(),
//                            .dropLast(4),
                        "lat" to current["lat"].toString(),
                        "long" to current["long"].toString(),
//                        "temp" to current["temp"].toString(),
                        "battery" to "${current["battery"]}%",
//                        "gps_acc" to current["gps_acc"].toString(),
                        "speed" to current["speed"].toString()
                    )
                }
                Log.d("OldServerAPI/drawMap", "id: $id, size: ${data.size}")
            } catch (e: Exception) {
                data.clear()
                data["1"] = mapOf("error" to "ERROR")
                Log.e("OldServerAPI/drawMap", e.toString())
            }


        }
    }

    fun setData(layout: LinearLayout) {
        drawMap()
        layout.removeAllViews()
        var tv: TextView
        var layoutHorisontal: LinearLayout
        try {
            for (i in data.keys) {
                layoutHorisontal = LinearLayout(layout.context)
                layoutHorisontal.orientation = LinearLayout.HORIZONTAL
                val currentData = data[i]
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
            Log.e("OldServerAPI/setData", e.toString())
            tv = TextView(layout.context)
            tv.text = e.cause.toString()
            layout.addView(tv)
        }


    }

    fun setProtectSystemOn() {
        GlobalScope.launch(Dispatchers.IO) {
            urlRequest("SignalingStatusOn")
        }
    }

    fun setProtectSystemOff() {
        GlobalScope.launch(Dispatchers.IO) {
            urlRequest("SignalingStatusOff")
        }
    }

    companion object {
        val instance = OldServerAPI()
    }

}
