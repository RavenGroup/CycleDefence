package com.example.prototype.layout

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roompointssample.PointListAdapter
import com.example.prototype.R
import com.example.prototype.database.DatabaseInstance
import com.example.prototype.database.PointViewModel
import com.example.prototype.database.ViewModelFactory

class DataActivity : AppCompatActivity() {
    private val pointViewModel: PointViewModel by viewModels {
        ViewModelFactory(DatabaseInstance.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_activity)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PointListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        pointViewModel.allPoints.observe(owner = this) { points ->
            // Update the cached copy of the words in the adapter.
            points.let { adapter.submitList(it) }
        }
/*
        Snackbar.make(view, "database open", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
*/

    }

}