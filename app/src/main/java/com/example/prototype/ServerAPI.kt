package com.example.prototype

///*

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
    private val links by lazy {
        mapOf<String, String>(
            "base" to "/MobileApp",
            "defenceOn" to "SignalingStatusOn",
            "defenceOff" to "SignalingStatusOff"
        )
    }
    var url: String = "http://128.75.198.121"
        set(value) {
            field = value + links["base"]
        }

    fun getAllData() {

    }
}
//*/