package com.example.prototype.database.points_core

import androidx.room.*
import com.example.prototype.database.dataclasses.Coordinates
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {
    @Query("SELECT * FROM points_table")
    fun getAllPoints(): Flow<List<Point>>

    @Query("SELECT * FROM points_table WHERE ID IN (:ids)")
    fun getPointsByIds(ids: IntArray): Flow<List<Point>>

    @Query("SELECT lat, long FROM points_table")
    fun getAllPointsCoordinates(): Flow<List<Coordinates>>

    @Update
    suspend fun updatePoints(vararg points: Point)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoints(vararg point: Point)

    @Delete
    suspend fun deletePoints(vararg points: Point)
}