package com.example.prototype

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.volley.ParseError
import com.android.volley.VolleyError
import com.example.prototype.database.DatabaseInstance
import com.example.prototype.database.points_core.Point
import org.json.JSONArray
import org.json.JSONObject


object ServerAPI {
    private val TAG = "ServerAPI"
    private lateinit var basicContext: Context

    private lateinit var key: String
    private fun setKey(value: String) {
        key = value
        val editorSP = SP.edit()
        editorSP.putString("connectionKey", value)
        editorSP.apply()
    }

    lateinit var uid: String
        private set

    fun setUid(value: String) {
        if (value == "") return
        uid = value
        val editorSP = SP.edit()
        editorSP.putString("userID", value)
        editorSP.apply()
    }

    lateinit var server: String
        private set

    fun setServer(value: String) {
        if (value == "") return
        server = value
        val editorSP = SP.edit()
        editorSP.putString("serverIP", value)
        editorSP.apply()
    }

    lateinit var tokenFCM: String
        private set

    fun setTokenFCM(token: String) {
        if (token == "") return
        tokenFCM = token
        val editorSP = SP.edit()
        editorSP.putString("tokenFCM", token)
        editorSP.apply()
    }


    interface DatabaseUpdateListener : Requests.ResponseListener {

        override fun onResponse(jsonData: JSONObject) {
            Log.d("$TAG/onResponse", jsonData.toString())
//            try {
            val jsonArray = jsonData.getJSONArray("data")
            Log.d("$TAG/onResponse", "Data received key: $key, count: ${jsonArray.length()}")
            var current: JSONArray
            val result = mutableListOf<Point>()
            for (i in 0 until jsonArray.length()) {
                current = jsonArray.getJSONArray(i)
//                            Log.d("ServerAPI/jsonDataPrint", current.toString())
                result.add(
                    Point(
                        current.getString(0),
                        current.getString(1),
                        current.getString(2).toFloat(),
                        current.getString(3).toFloat(),
                        current.getString(4),
                        current.getInt(5),
                        current.getString(6).toFloat()
                    )
                )
            }
            DatabaseInstance.pointDatabase.addPoints(*result.toTypedArray())

            setKey(jsonData.getString("key"))
            if (jsonData.getString("needMore") == "1") {
                // TODO: check for recursion error
                updateData()
            }

            Log.d("$TAG/updateData/onResponse", "FINISH")

        }

        override fun onError(errorData: VolleyError) {
            if (errorData is ParseError) {
                updateNotRequired()
                throw Requests.UpdateNotNecessary()
            }
            Log.e("$TAG/updateData/onError", errorData.toString())
        }

        fun updateNotRequired() {
            Log.d("$TAG/updateData", "All is up to date")
        }
    }

    private val SP: SharedPreferences by lazy {
        Log.d("$TAG/ContextMode", Context.MODE_PRIVATE.toString())
        basicContext.getSharedPreferences("Values", Context.MODE_PRIVATE)
    }

    fun start(context: Context) {
        Log.d("$TAG/start", "write data")
        basicContext = context.applicationContext
        Requests.createRequestQueue(basicContext)
        key = SP.getString("connectionKey", "0") ?: "ERROR"
        uid = SP.getString("userID", "4") ?: "ERROR"
//        server = SP.getString("connectionKey", "http://37.147.182.252") ?: "ERROR"
        server = SP.getString(
            "serverIP",
//            "https://webhook.site/be2f30e7-c238-4ffd-bbed-b2a18d0961c2"
//            "http://37.147.182.252"
//            "https://bdirmw.deta.dev"
            "http://95.30.191.78"
        ) ?: "ERROR"
        tokenFCM = SP.getString("tokenFCM", "ERROR") ?: "ERROR"
    }


    private object Links {
        val base by lazy { "/MobileApp" }
        val defenceOn by lazy { "/Signaling/1" }
        val defenceOff by lazy { "/Signaling/0" }
        val updateData by lazy { "/UpdateData" }
        val logIn by lazy { "/Login" }
    }


    fun updateData(
        listener: Requests.ResponseListener? = null
    ) {
        Requests.jsonRequest(
            basicContext,
            server + Links.base + Links.updateData,
            JSONObject("{\"id\": $uid, \"key\": $key}"),
            listener ?: object : DatabaseUpdateListener {}
        )

    }

    fun turnDefenceSystemOn(
//        context: Context = basicContext,
        listener: Requests.ResponseListener = object : Requests.ResponseListener {}
    ) {
        Requests.simpleRequest(basicContext, server + Links.defenceOn, uid, listener)

    }

    fun turnDefenceSystemOff(
//        context: Context = basicContext,
        listener: Requests.ResponseListener = object : Requests.ResponseListener {}
    ) {
        Requests.simpleRequest(basicContext, server + Links.defenceOff, uid, listener)

    }

    fun logIn() {
        Requests.jsonRequest(
            basicContext,
            server + Links.base + Links.logIn,
            JSONObject(mapOf("id" to uid, "fcm_token" to tokenFCM)),
            listener = object : Requests.ResponseListener {
                override fun onResponse(jsonData: JSONObject) {
                    Log.d("$TAG/logInt", jsonData.toString())
                }
            }
        )
    }
}