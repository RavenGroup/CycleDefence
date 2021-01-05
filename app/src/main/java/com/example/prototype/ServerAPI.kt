package com.example.prototype

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.json.JSONObject

/*
    time: String,
    date: String,
    lat: Float,
    long: Float,
    temp: Float,
    battery: Int,
    speed: Float
 */


object ServerAPI {
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

    private val SP: SharedPreferences by lazy {
        Log.d("ServerAPI/ContextMode", Context.MODE_PRIVATE.toString())
        basicContext.getSharedPreferences("Values", Context.MODE_PRIVATE)
    }

    fun start(context: Context) {
        Log.d("ServerAPI", "its me")
        basicContext = context.applicationContext
        Requests.createRequestQueue(basicContext)
        key = SP.getString("connectionKey", "0") ?: "ERROR"
        uid = SP.getString("userID", "4") ?: "ERROR"
//        server = SP.getString("connectionKey", "http://37.147.182.252") ?: "ERROR"
        server = SP.getString(
            "serverIP",
//            "https://webhook.site/be2f30e7-c238-4ffd-bbed-b2a18d0961c2"
//            "http://37.147.182.252"
            "https://bdirmw.deta.dev"
        ) ?: "ERROR"
    }


    private object Links {
        val base by lazy { "/MobileApp" }
        val defenceOn by lazy { "/Signaling/1" }
        val defenceOff by lazy { "/Signaling/0" }
        val getAllData by lazy { "/GetAllData" }
        val updateData by lazy { "/UpdateData" }
    }



    fun updateData(
        listener: Requests.ResponseListener? = null
    ) {
        Requests.jsonRequest(
            basicContext,
            server + Links.base + Links.updateData,
            JSONObject("{\"id\": $uid, \"key\": $key}"),
            listener ?: object : Requests.ResponseListener {}
        )
        /*GlobalScope.launch(Dispatchers.IO) {
                        val db = AppDatabase.getDatabase(basicContext).userDao()
                        val jsonArr = data.getJSONArray("data")
                        db.insert(
                            Point(
                                0,
                                jsonArr.getString(0),
                                jsonArr.getString(1),
                                jsonArr.getString(2).toFloat(),
                                jsonArr.getString(3).toFloat(),
                                jsonArr.getString(4).toFloat(),
                                jsonArr.getInt(5),
                                jsonArr.getString(6).toFloat()
                            )
                        )
                    }*/
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
}