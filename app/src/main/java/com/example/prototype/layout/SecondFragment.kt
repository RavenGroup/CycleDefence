package com.example.prototype.layout


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.VolleyError
import com.example.prototype.R
import com.example.prototype.ServerAPI
import kotlinx.android.synthetic.main.fragment_second.*
import org.json.JSONArray
import org.json.JSONObject


class SecondFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        old setData(dataOutput)

        log_out_btn.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//            this.activity?.finish()
//            finish()

        }
// FIX: if fragment is close before server's answer, error will occur
//        Requests.createRequestQueue(this.requireContext().applicationContext)
        refresh_btn.setOnClickListener {
            refresh_btn.isEnabled = false
            ServerAPI.updateData(listener = object :
                    ServerAPI.DatabaseUpdateListener {
                override fun onResponse(jsonData: JSONObject) {
                    super.onResponse(jsonData)
                    Log.d("ServerAPI/update", jsonData.toString())
                    if (dataOutput == null) return

                    dataOutput.removeAllViews()
                    var tv: TextView
                    var layoutHorisontal: LinearLayout
                    var current: JSONArray
                    try {
                        val allData = jsonData.getJSONArray("data")

                        for (i in 0 until allData.length()) {
                            current = allData.getJSONArray(i)
                            layoutHorisontal = LinearLayout(dataOutput.context)
                            layoutHorisontal.orientation = LinearLayout.HORIZONTAL
                            layoutHorisontal = LinearLayout(dataOutput.context)
                            layoutHorisontal.orientation = LinearLayout.HORIZONTAL
//                            Log.d("ServerAPI/dataPrint", current.toString())
                            for (ii in 0..6) {
//                                    Log.d("ServerAPI/dataPrintII", ii.toString())
                                tv = TextView(dataOutput.context)
                                tv.setPaddingRelative(20, 0, 0, 10)
                                tv.text = current[ii].toString()
                                layoutHorisontal.addView(tv)
                            }
                            dataOutput.addView(layoutHorisontal)
                        }
                    } catch (e: Exception) {
                        Log.e("ServerAPI/updateData", e.toString())
                    }
                    refresh_btn?.isEnabled = true
                    Toast.makeText(
                            this@SecondFragment.context?.applicationContext,
                            "success!",
                            Toast.LENGTH_SHORT
                    ).show()

                }

                override fun onError(errorData: VolleyError) {
                    if (this@SecondFragment.context == null) {
                        return
                    }
                    super.onError(errorData)

                    Toast.makeText(
                            this@SecondFragment.context?.applicationContext,
                            "An error has occurred",
                            Toast.LENGTH_SHORT
                    ).show()


                }

                override fun updateNotRequired() {
                    Toast.makeText(
                            this@SecondFragment.context?.applicationContext,
                            "Everything is up to date",
                            Toast.LENGTH_SHORT
                    ).show()
                    refresh_btn?.isEnabled = true
                }

                override fun serverIsNotAvailable() {
                    Toast.makeText(
                            this@SecondFragment.context?.applicationContext,
                            "Server is not available",
                            Toast.LENGTH_SHORT
                    ).show()
                    return
                }
            }
            )

        }
        turnSystemOn.setOnClickListener {
            ServerAPI.turnDefenceSystemOn()
        }
        turnSystemOff.setOnClickListener {
            ServerAPI.turnDefenceSystemOff()
        }

    }
}