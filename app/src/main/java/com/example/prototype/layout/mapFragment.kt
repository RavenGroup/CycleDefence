package com.example.prototype.layout

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.prototype.R
import com.example.prototype.ServerAPI
import com.example.prototype.database.DatabaseInstance
import com.example.prototype.database.dataclasses.Coordinates
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@kotlinx.coroutines.InternalCoroutinesApi
class mapFragment : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refreshButton.isVisible = false
        refreshButton.setOnClickListener {
            try {
                ServerAPI.updateData(object : ServerAPI.DatabaseUpdateListener {
                    override fun updateNotRequired() {
                        if (this@mapFragment.context == null) return
                        Toast.makeText(
                                this@mapFragment.context?.applicationContext,
                                "Everything is up to date",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun serverIsNotAvailable() {
                        if (this@mapFragment.context == null) return
                        Toast.makeText(
                                this@mapFragment.context?.applicationContext,
                                "Server is not available",
                                Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            } catch (e: Exception) {
                Log.d("mapFragment/update", e.cause.toString())
            }

        }
    }

    private val dataFlow: Flow<List<Coordinates>> by lazy { DatabaseInstance.pointDatabase.allCoordinates }
    private lateinit var map: GoogleMap

    //    @Volatile
    private val polylineOptions by lazy { PolylineOptions() }

    val TAG = "mapFragment"

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        polylineOptions.apply {
            color(Color.rgb((0..256).random(), (0..256).random(), (0..256).random()))
            width(10f)
            startCap(RoundCap())
            endCap(RoundCap())
        }
        displayData()
        refreshButton.isVisible = true
    }


    private fun displayData() = CoroutineScope(Dispatchers.IO).launch {
        Log.d("$TAG/Scope", this.toString())
        dataFlow.collect {
            if (this@mapFragment.context == null) {
                cancel()
            }
            if (it.isEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(this@mapFragment.context, "Please refresh", Toast.LENGTH_SHORT).show()
                }
                return@collect
            }
            Log.d("$TAG/displayData/dataReceivedFromDB", "Coordinates count:" + it.size.toString())
//            (polylineOptions.points.size - 1)..it.size) TODO dynamic appending new points
            polylineOptions.points.clear()
            polylineOptions.addAll(
                    it.map { coord -> LatLng(coord.lat, coord.lng) }
            )

            CoroutineScope(Dispatchers.Main).launch {
                map.clear()
                map.addPolyline(polylineOptions)
                map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                        it.last().lat,
                                        it.last().lng
                                ), 14f
                        )
                )

            }

        }
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}
