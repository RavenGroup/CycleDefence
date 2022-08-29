package com.example.prototype.database.points_core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

/*
time: String,
date: String,
lat: Float,
long: Float,
temp: Int,
battery: Int,
speed: Float
*/



@Database(entities = arrayOf(Point::class), version = 1, exportSchema = false)
abstract class PointDatabase : RoomDatabase() {
    abstract fun pointDao(): PointDao

    /*
    val db = Room.databaseBuilder(
        applicationContext,
        PointDatabase::class.java, "database-name"
    ).build()
*/
    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: PointDatabase? = null

        fun getDatabase(
            context: Context
//            scope: CoroutineScope
        ): PointDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PointDatabase::class.java,
                        "ApplicationDatabase"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
        }
    }
}


//}