package com.example.weatherapp.Settings.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.CustomConfermation.AlertButtonResult
import com.example.weatherapp.Utils.CustomConfermation.UtilsDialog
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class SettingsMapFragment: Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var geoCoder: Geocoder

    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private lateinit var alertButtonResult: AlertButtonResult


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor=sharedPreference.edit()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_map, container, false)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        geoCoder  = Geocoder(requireActivity())

        Places.initialize(requireContext(), Constants.GOOGL_MAP_API_KEY)
        //todo what is that
        val placesClient = Places.createClient(requireContext())

        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES)

        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )


        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.i("Erorr", "Places error : $status")
            }

            override fun onPlaceSelected(palce: Place) {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(palce.latLng)
                        .title(palce.name))

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(palce.latLng,10f))            }
        })




        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMaps: GoogleMap) {
        googleMap = googleMaps

        googleMap.setOnMapLongClickListener {
            val address =geoCoder.getFromLocation(it.latitude, it.longitude, 1)

            address?.let {
                if (it.isNotEmpty()){
                    var data = it[0]
                    val currentLocation = MapModel(
                        (data.latitude.toString()+data.longitude.toString()),
                        data.countryName,
                        data.adminArea,
                        data.latitude,
                        data.longitude
                    )

                    alertButtonResult= object : AlertButtonResult {
                        override fun IfOk() {
                            Toast.makeText(requireContext(), "${getString(R.string.message_Location_saved)}  $currentLocation ", Toast.LENGTH_LONG).show()

                            editor.putString("latitude", currentLocation.latitude.toString())
                            editor.putString("longitude", currentLocation.longitude.toString())
                            editor.apply()
                            findNavController().navigate(R.id.homeFragment)                        }

                        override fun IfCancel() {
                            Toast.makeText(requireContext(),getString(R.string.message_Location_canceld), Toast.LENGTH_SHORT).show()
                            //findNavController().navigate(R.id.homeFragment)

                        }
                    }
                    UtilsDialog .showDialog(getString(R.string.title_save_current_loc),
                        getString(R.string.message_save_current_loc),
                        alertButtonResult,requireContext())
                }

            }


        }


        googleMap.isMyLocationEnabled  =true

        googleMap.setOnMapClickListener {
            googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("THis")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it,10f))
        }


        googleMap.setOnMarkerClickListener { marker ->
            val address =geoCoder.getFromLocation(marker.position.latitude, marker.position.longitude, 1)

            address?.let {
                if (it.isNotEmpty()){
                    var data = it[0]
                    val currentLocation = MapModel(
                        (data.latitude.toString()+data.longitude.toString()),
                        data.countryName,
                        data.adminArea,
                        data.latitude,
                        data.longitude
                    )

                    UtilsDialog.showDialog(getString(R.string.title_save_current_loc),
                        getString(R.string.message_save_current_loc),
                        alertButtonResult,requireContext())
                }

            }

            true
        }


    }
}