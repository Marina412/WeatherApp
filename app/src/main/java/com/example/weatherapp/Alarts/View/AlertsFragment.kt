package com.example.weatherapp.Alarts.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAlertsBinding


class AlertsFragment : Fragment() {

lateinit var binding: FragmentAlertsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAlertsBinding.inflate(inflater,container,false)


        return binding.root
    }

}