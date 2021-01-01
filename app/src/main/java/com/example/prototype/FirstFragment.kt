package com.example.prototype

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        log_in_btn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            OldServerAPI.instance.setId(user_id_te.text.toString())
            OldServerAPI.instance.setUrl(ip_address_te.text.toString())
        }
        showMap.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_MapFragment)
            OldServerAPI.instance.setId(user_id_te.text.toString())
            OldServerAPI.instance.setUrl(ip_address_te.text.toString())
        }
    }
}