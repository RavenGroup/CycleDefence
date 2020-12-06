package com.example.prototype

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController


// all elements from activity
import kotlinx.android.synthetic.main.fragment_second.*
/*
// http requests
import khttp.get as GET
import khttp.post as POST
// coroutines for asynchronous http requests
import kotlinx.coroutines.Dispatchers
// Base exception for catching all errors
import java.lang.Exception
*/

//128.75.198.121
/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


class SecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.log_out_btn).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        refresh_btn.setOnClickListener{
            ServerAPI().getData()
        }

    }
}
