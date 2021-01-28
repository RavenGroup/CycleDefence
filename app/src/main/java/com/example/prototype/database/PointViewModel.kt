package com.example.prototype.database

import androidx.lifecycle.*
import com.example.prototype.database.DataRepository
import com.example.prototype.database.points_core.Point
import kotlinx.coroutines.launch

class PointViewModel(
    private val repository: DataRepository
) : ViewModel() {
    val allPoints: LiveData<List<Point>> = repository.allPointsData.asLiveData()


    fun addPoints(vararg points: Point) {
        viewModelScope.launch {
            repository.addPoints(*points)
        }
    }
}
