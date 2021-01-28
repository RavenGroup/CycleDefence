package com.example.prototype.database

import androidx.annotation.WorkerThread
import com.example.prototype.database.points_core.Point
import com.example.prototype.database.points_core.PointDao
import kotlinx.coroutines.flow.Flow


class DataRepository(private val pointDao: PointDao) {
    val allPointsData: Flow<List<Point>> = pointDao.getAllPoints()

    @WorkerThread
    suspend fun addPoints(vararg points: Point) {
        pointDao.insertPoints(*points)
    }

    fun getAllPoints(): Flow<List<Point>> {
        return pointDao.getAllPoints()
    }
}