package com.example.prototype.database.points_core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points_table")
data class Point(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val pid: Int, // Point ID

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "lat")
    val lat: Float,

    @ColumnInfo(name = "long")
    val lng: Float,

    @ColumnInfo(name = "temp")
    val tmp: String,

    @ColumnInfo(name = "charge")
    val crg: Int,

    @ColumnInfo(name = "speed")
    val spd: Float
) {
    constructor(
        time: String,
        date: String,
        lat: Float,
        lng: Float,
        tmp: String,
        crg: Int,
        spd: Float
    ) : this(0, time, date, lat, lng, tmp, crg, spd)

    override fun toString(): String {
        return "$pid time: $time, date: $date, lat: $lat, lng: $lng, tmp: $tmp, crg: $crg, spd: $spd"
    }
}