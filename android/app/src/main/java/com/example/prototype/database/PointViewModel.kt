package com.example.prototype.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.prototype.database.dataclasses.Coordinates
import com.example.prototype.database.points_core.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PointViewModel(
    private val repository: DataRepository
) : ViewModel() {
    val allPoints: LiveData<List<Point>> = repository.allPointsData.asLiveData()
    val allCoordinates: Flow<List<Coordinates>> = repository.allPointsCoordinates


    fun addPoints(vararg points: Point) {
        viewModelScope.launch {
            repository.addPoints(*points)
        }
    }
}
