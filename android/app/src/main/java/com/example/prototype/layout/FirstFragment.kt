package com.example.prototype.layout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prototype.R
import com.example.prototype.ServerAPI
import kotlinx.android.synthetic.main.fragment_first.*


class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ip_address_te.setText(ServerAPI.server)
        user_id_te.setText(ServerAPI.uid)

        log_in_btn.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//            val intent: Intent = Intent(this.context, SecondFragment::class.java)
//                .apply {putExtra()}
//            startActivity(intent)

        }
        showMap.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_MapFragment)
        }
        btnSaveData.setOnClickListener {
            ServerAPI.setServer(ip_address_te.text.toString())
            ServerAPI.setUid(user_id_te.text.toString())
            ServerAPI.logIn()

        }
        show_data_btn.setOnClickListener {
            val intent = Intent(this.context, DataActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this.context, "sometime it will work", Toast.LENGTH_SHORT).show()

        }
    }
}