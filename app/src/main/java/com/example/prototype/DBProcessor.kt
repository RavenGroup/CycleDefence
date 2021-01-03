package com.example.prototype

import androidx.room.*

/*
time: String,
date: String,
lat: Float,
long: Float,
temp: Int,
battery: Int,
speed: Float
*/

object DBProcessor {
    @Entity
    data class Pkg(
        @PrimaryKey val pid: Int, // pkg ID
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
        @ColumnInfo(name = "battery")
        val bat: Int,
        @ColumnInfo(name = "speed")
        val spd: Float
    )

    @Dao
    interface PkgDao {
        @Query("SELECT * FROM pkg")
        fun getAll(): List<Pkg>

        @Query("SELECT * FROM pkg WHERE pid IN (:pids)")
        fun loadAllByIds(pids: IntArray): List<Pkg>


        @Insert
        fun insertAll(vararg pkgs: Pkg)

        @Delete
        fun delete(pkg: Pkg)
    }

    @Database(entities = arrayOf(Pkg::class), version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): PkgDao
    }
/*
    val db = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database-name"
    ).build()
*/


}