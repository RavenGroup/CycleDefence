package com.example.prototype

import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception

class mapFragment : Fragment() {
    //    private var places: Map<String> =


    private val callback = OnMapReadyCallback { googleMap ->
        try {
            ServerAPI.instance.drawMap()
        } catch (e: Exception) {
            Log.d("Map/Server", e.toString())
        }
        Log.d("Map/places", ServerAPI.instance.places.toString())
        val polylineOptions = PolylineOptions()

        for (i in ServerAPI.instance.places.keys) {
            polylineOptions.add(
                LatLng(
                    ServerAPI.instance.places[i]?.get("lat")?.toDouble() ?: 0.0,
                    ServerAPI.instance.places[i]?.get("long")?.toDouble() ?: 0.0
                )
            )
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        ServerAPI.instance.places[i]?.get("lat")?.toDouble() ?: 0.0,
                        ServerAPI.instance.places[i]?.get("long")?.toDouble() ?: 0.0
                    )
                )
            )
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(15f))

        }
        val polyline = googleMap.addPolyline(
            polylineOptions.color(Color.BLUE).width(3f)
        )
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()


//            Log.d("Map/Draw", place.toString())
/*
            googleMap.addMarker(
                MarkerOptions().position(place)
                    .title(ServerAPI.instance.places[i]?.get("time") ?: "Where are you?")
                    .zIndex((0.001 * i.toInt()).toFloat())
            )
 */
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}