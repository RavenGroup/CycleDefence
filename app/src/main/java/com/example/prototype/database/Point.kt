package com.example.prototype.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_table")
data class Point(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val pid: Int, // Point ID

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "lat")
    val lat: Float,

    @ColumnInfo(name = "long")
    val lng: Float,

    @ColumnInfo(name = "temp")
    val tmp: Float,

    @ColumnInfo(name = "charge")
    val crg: Int,

    @ColumnInfo(name = "speed")
    val spd: Float
)