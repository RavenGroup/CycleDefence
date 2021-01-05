package com.example.prototype.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val readAllPoints: LiveData<List<Point>>
    private val repository: DataRepository

    init {
        val dataDao = AppDatabase.getDatabase(application).userDao()
        repository = DataRepository(dataDao)
        readAllPoints = repository.readAllData
    }

    fun addPoint(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPoint(point)
        }
    }
}