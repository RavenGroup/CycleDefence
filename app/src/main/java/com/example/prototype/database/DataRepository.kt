package com.example.prototype.database

import androidx.lifecycle.LiveData

class DataRepository(private val pointDao: PointDao) {
    val readAllData: LiveData<List<Point>> = pointDao.getAllPoints()
    suspend fun addPoint(point: Point) {
        pointDao.insertPoints(point)
    }
}