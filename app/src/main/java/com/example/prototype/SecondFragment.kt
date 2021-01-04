package com.example.prototype


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_second.*


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
        }
        Requests.createRequestQueue(this.requireContext())
        refresh_btn.setOnClickListener {
            ServerAPI.getAllData(this.requireContext())

        }
        turnSystemOn.setOnClickListener {
            ServerAPI.turnDefenceSystemOn()
        }
        turnSystemOff.setOnClickListener {
            ServerAPI.turnDefenceSystemOff()
        }

    }
}
