package com.example.prototype

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.maps.model.RoundCap

class mapFragment : Fragment() {
    //    private var data: Map<String> =


    private val callback = OnMapReadyCallback { googleMap ->
        try {
            OldServerAPI.instance.drawMap()
        } catch (e: Exception) {
            Log.d("Map/Server", e.toString())
        }
        Log.d("Map/data", OldServerAPI.instance.data.toString())
        val polylineOptions = PolylineOptions()

        for (i in OldServerAPI.instance.data.keys) {
//            if (ServerAPI.instance.data[i]?.get("gps_acc").toString() == "V"){
//                continue
//            }
            polylineOptions.add(
                LatLng(
                    OldServerAPI.instance.data[i]?.get("lat")?.toDouble() ?: 0.0,
                    OldServerAPI.instance.data[i]?.get("long")?.toDouble() ?: 0.0
                )
            )
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLng(
                    LatLng(
                        OldServerAPI.instance.data[i]?.get("lat")?.toDouble() ?: 0.0,
                        OldServerAPI.instance.data[i]?.get("long")?.toDouble() ?: 0.0
                    )
                )
            )
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(16f))

        }
        val polyline = googleMap.addPolyline(
            polylineOptions.color(Color.BLUE).width(10f)
        )
        polyline.startCap = RoundCap()
        polyline.endCap = RoundCap()


//            Log.d("Map/Draw", place.toString())
/*
            googleMap.addMarker(
                MarkerOptions().position(place)
                    .title(ServerAPI.instance.data[i]?.get("time") ?: "Where are you?")
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