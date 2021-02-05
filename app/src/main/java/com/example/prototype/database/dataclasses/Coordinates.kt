package com.example.prototype.database.dataclasses

import androidx.room.ColumnInfo


data class Coordinates(
    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "long")
    val lng: Double
){
    override fun toString(): String {
        return "lat: $lat, long: $lng"
    }
}