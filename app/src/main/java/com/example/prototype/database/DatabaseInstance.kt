package com.example.prototype.database

import android.content.Context
import com.example.prototype.database.points_core.PointDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

object DatabaseInstance
//private constructor()
{
    //    companion object {
    lateinit var context: Context

    //    private val applicationScope = CoroutineScope(SupervisorJob())
    private val pointDB by lazy {
        PointDatabase.getDatabase(
            context
//        , applicationScope
        )
    }
    val repository by lazy { DataRepository(pointDB.pointDao()) }

    //        val pointDatabase by lazy { PointViewModel(repository) }
    val pointDatabase by lazy {
        PointViewModel(
            repository
        )
    }

//    }
}