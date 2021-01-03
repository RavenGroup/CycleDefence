package com.example.prototype

import android.content.Context

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
    // TODO: 03.01.21 move it to SharedPreference
    private var key: String = "test"
    private var uid: String = "4"
    var server: String = "http://37.147.182.252/MobileApp"
        set(value) {
            field = value + Links.base
        }

    lateinit var basicContext: Context

    private object Links {
        val base by lazy { "/MobileApp" }
        val defenceOn by lazy { "/Signaling/1" }
        val defenceOff by lazy { "/Signaling/0" }
        val getAllData by lazy { "/DataSet/All" }
        val getData by lazy { "" }
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