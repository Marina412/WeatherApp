package com.example.weatherapp.Settings.View

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FirstLocationDailogBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class FirstLocationDailog : DialogFragment() {
    lateinit var binding: FirstLocationDailogBinding
    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    lateinit var mFusedLocationClient: FusedLocationProviderClient


    lateinit var locationPermissionRequest :ActivityResultLauncher<Array<String>>
    private val parentJob = Job()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FirstLocationDailogBinding.inflate(inflater, container, false)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        sharedPreference =
            requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor = sharedPreference.edit()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rdBtnGroupLocationMode.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {
                binding.rdBtnMap -> {
                    if (UtilsFunction.isOnline(requireContext())) {
                        findNavController().navigate(R.id.settingsMapFragment)
                        //findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), R.string.offline_mode, Toast.LENGTH_LONG)
                            .show()
                    }
                }

                binding.rdBtnGps -> {
                    if (UtilsFunction.isOnline(requireContext())) {
                        getLastLocation()
                       // findNavController().navigate(R.id.homeFragment)

                        dismiss()
                    } else {
                        Toast.makeText(requireContext(), R.string.offline_mode, Toast.LENGTH_LONG)
                            .show()
                    }


                }
            }

        }
    }


    override fun onStart() {
        super.onStart()
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {


        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

         locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    requestNewLocationData()
                    //////
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                    requestNewLocationData()

                }
                else -> {
                    // No location access granted.
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {

            if (requestCode == Constants.PERMISSION_ID) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation()
                }

            }
        }


    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @SuppressLint("MissingPermision")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData()
            } else {
                Toast.makeText(requireContext(), "plz turn on location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        val location = mFusedLocationClient.getCurrentLocation(
            CurrentLocationRequest
                .Builder()
                .build(), null
        ).addOnSuccessListener {
                location->

            editor.putString("latitude",location.latitude.toString())
            editor.putString("longitude", location.longitude.toString())
            editor.commit()
            Log.i("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm", "requestNewLocationData: ${location.latitude}   ${location.longitude}")


                findNavController().navigate(R.id.action_firstLocationDailog_to_homeFragment)

        }

    }

    /*private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val mLastLocation: Location? = locationResult.getLastLocation()
            if (mLastLocation != null) {
                editor.putString("latitude", mLastLocation.latitude.toString())
                editor.putString("longitude", mLastLocation.longitude.toString())
                editor.commit()
            }
        }

    }*/


}