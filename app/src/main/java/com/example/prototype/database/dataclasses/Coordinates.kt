package com.example.prototype.database.dataclasses

import androidx.room.ColumnInfo


data class Coordinates(
    @ColumnInfo(name = "lat")
    val lat: String,

    @ColumnInfo(name = "long")
    val lng: String
)