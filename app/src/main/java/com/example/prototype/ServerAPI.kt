package com.example.prototype

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

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
    fun setKey(value: String) {
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
        basicContext = context
        key = SP.getString("connectionKey", "0") ?: "ERROR"
        uid = SP.getString("userID", "4") ?: "ERROR"
//        server = SP.getString("connectionKey", "http://37.147.182.252") ?: "ERROR"
        server = SP.getString(
            "serverIP",
            "https://webhook.site/be2f30e7-c238-4ffd-bbed-b2a18d0961c2"
        ) ?: "ERROR"
    }


    private object Links {
        val base by lazy { "/MobileApp" }
        val defenceOn by lazy { "/Signaling/1" }
        val defenceOff by lazy { "/Signaling/0" }
        val getAllData by lazy { "/DataSet/All" }
        val update by lazy { "/DataSet/update" }
    }

    fun getAllData(
        context: Context = basicContext,
        listener: Requests.ResponseListener = object : Requests.ResponseListener {}
    ) {
        Requests.simpleRequest(context, server + Links.getAllData, uid, listener)
//    Log.d("ServerAPI/getAllData", server)
    }

    fun turnDefenceSystemOn(
        context: Context = basicContext,
        listener: Requests.ResponseListener = object : Requests.ResponseListener {}
    ) {
        Requests.simpleRequest(context, server + Links.defenceOn, uid, listener)

    }

    fun turnDefenceSystemOff(
        context: Context = basicContext,
        listener: Requests.ResponseListener = object : Requests.ResponseListener {}
    ) {
        Requests.simpleRequest(context, server + Links.defenceOff, uid, listener)

    }
}