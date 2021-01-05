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
import kotlinx.android.synthetic.main.fragment_second.*
import org.json.JSONArray
import org.json.JSONObject

class mapFragment : Fragment() {
    //    private var data: Map<String> =


    private val callback = OnMapReadyCallback { googleMap ->
//
        val lat = 1
        val lng = 2


        val polylineOptions = PolylineOptions()
//        new code
        ServerAPI.updateData(listener = object : Requests.ResponseListener {
            override fun onResponse(data: JSONObject) {

                Log.d("mapFragment/update", data.toString())
                if (dataOutput == null) return

                var current: JSONArray
                try {
                    val allData = data.getJSONArray("data")

                    for (i in 0 until allData.length()) {
                        current = allData.getJSONArray(i)
                        Log.d("mapFragment/dataPrint", current.toString())
                        polylineOptions.add(
                            LatLng(
                                current.getDouble(lat),
                                current.getDouble(lng)
                            )
                        )
                    }
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                allData.getJSONArray(allData.length() - 1).getDouble(lat),
                                allData.getJSONArray(allData.length() - 1).getDouble(lng)
                            )
                        )
                    )
                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(16f))
                } catch (e: Exception) {
                    Log.e("mapFragment/updateData", e.toString())
                }
                val polyline = googleMap.addPolyline(
                    polylineOptions.color(Color.BLUE).width(10f)
                )
                polyline.startCap = RoundCap()
                polyline.endCap = RoundCap()
            }
        })
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
