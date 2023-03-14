package com.example.weatherapp.Settings.View

import android.annotation.SuppressLint
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.google.android.gms.location.*
import java.util.*

///////todo shard init
class SettingsFragment : Fragment() {
lateinit var binding: FragmentSettingsBinding

lateinit var sharedPreference:SharedPreferences
lateinit var editor: SharedPreferences.Editor


lateinit var mFusedLocationClient: FusedLocationProviderClient

private var strLanguage:String=""
private var strTempUnit:String=""
private var strWindUnit:String=""
private var strNotification:String=""
private var strLocation:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding=FragmentSettingsBinding.inflate(inflater,container,false)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor=sharedPreference.edit()

//////////////////////////////////////////////////////////
        if(Locale.getDefault().getDisplayLanguage() == "العربية"){
            binding.rdBtnArabic.isChecked=true
            strLanguage=Constants.ARABIC
        }else{
            binding.rdBtnEnglish.isChecked=true
            strLanguage=Constants.ENGLISH
        }

//////////////////////////////////////////////////////////////////////////////////////
        binding.rdBtnGroupLocationMode.check(sharedPreference.getInt("isLocation", R.id.rdBtnGps))
        binding.rdBtnGroupLanguage.check(sharedPreference.getInt("isLanguage", R.id.rdBtnEnglish))
        binding.rdBtnGroupTempratureUnit.check(sharedPreference.getInt("isTempratureUnit", R.id.rdBtnCelsius))
        binding.rdBtnGroupWindUnit.check(sharedPreference.getInt("isWindUnit",R.id.rdBtnMeterSec))
        binding.rdBtnGroupNotification.check(sharedPreference.getInt("isNotification", R.id.rdBtnNotification))


        binding.rdBtnGroupLanguage.setOnCheckedChangeListener {
                _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {

                binding.rdBtnArabic->{
                    strLanguage = Constants.ARABIC
                }
                binding.rdBtnEnglish->{
                    strLanguage = Constants.ENGLISH
                }
            }

            editor.putInt("isLanguage",binding.rdBtnGroupLanguage.checkedRadioButtonId)
            editor.putString("language", strLanguage)
            editor.apply()
            UtilsFunction.changeLanguage(strLanguage,requireContext())

        }

        binding.rdBtnGroupTempratureUnit.setOnCheckedChangeListener {
                _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {

                binding.rdBtnCelsius->{
                    strTempUnit = Constants.CELSIUS
                }
                binding.rdBtnFahrenheit->{
                    strTempUnit = Constants.FAHRENHEIT
                }
                binding.rdBtnKelvin->{
                    strTempUnit = Constants.KELVIN
                }
            }
            editor.putInt("isTempratureUnit",binding.rdBtnGroupTempratureUnit.checkedRadioButtonId)
            editor.putString("tempUnit", strTempUnit)
            editor.apply()

        }

        binding.rdBtnGroupWindUnit.setOnCheckedChangeListener {
                _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {

                binding.rdBtnMeterSec->{
                    strWindUnit  = Constants.METERSEC
                }
                binding.rdBtnMilesHour->{
                    strWindUnit = Constants.MILESHOUR
                }
            }
            editor.putInt("isWindUnit",binding.rdBtnGroupWindUnit.checkedRadioButtonId)
            editor.putString("windUnit", strWindUnit)
            editor.apply()

        }


        binding.rdBtnGroupNotification.setOnCheckedChangeListener {
                _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {

                binding.rdBtnNotification->{
                    strNotification  = Constants.NOTIFICATIONS
                }
                binding.rdBtnAlerts->{
                    strNotification = Constants.ALERTS
                }
            }
            editor.putInt("isNotification",binding.rdBtnGroupNotification.checkedRadioButtonId)
            editor.putString("notification", strNotification)
            editor.apply()

        }

        binding.rdBtnGroupLocationMode.setOnCheckedChangeListener{
                _, checkedId ->
            val radio: RadioButton = view.findViewById(checkedId)
            when (radio) {

                binding.rdBtnMap->{

                    if(UtilsFunction.isOnline(requireContext()))
                    {
                        findNavController().navigate(R.id.settingsMapFragment)
                    }
                    else
                    {
                        Toast.makeText(requireContext(),R.string.offline_mode,Toast.LENGTH_LONG).show()
                    }
                }
                binding.rdBtnGps->{

                    if(UtilsFunction.isOnline(requireContext()))
                    {
                        getLastLocation()
                    }
                    else
                    {
                        Toast.makeText(requireContext(),R.string.offline_mode,Toast.LENGTH_LONG).show()
                    }


                }
            }

        }

    }
    override fun onStop() {
        super.onStop()


        editor.putInt("isLocation",binding.rdBtnGroupLocationMode.checkedRadioButtonId)
        editor.putInt("isLanguage",binding.rdBtnGroupLanguage.checkedRadioButtonId)
        editor.putInt("isTempratureUnit",binding.rdBtnGroupTempratureUnit.checkedRadioButtonId)
        editor.putInt("isWindUnit",binding.rdBtnGroupWindUnit.checkedRadioButtonId)
        editor.putInt("isNotification",binding.rdBtnGroupNotification.checkedRadioButtonId)

        editor.putString("language", strLanguage)
        editor.putString("tempUnit", strTempUnit)
        editor.putString("windUnit", strWindUnit)
        editor.putString("notification", strNotification)

        editor.apply()
    }
    

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun checkPermissions():Boolean{
        return ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(requireActivity(), arrayOf<String>(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.PERMISSION_ID)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == Constants.PERMISSION_ID){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }

    private fun isLocationEnabled():Boolean{
        val locationManager: LocationManager =requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @SuppressLint("MissingPermision")
    private fun getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                requestNewLocationData()
            }
            else{
                Toast.makeText(requireContext(),"plz turn on location", Toast.LENGTH_SHORT).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            requestPermissions()
        }

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(){
        val mLocationRequest= LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(requireActivity())
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper())

    }

    private val mLocationCallback: LocationCallback =object : LocationCallback(){
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val mLastLocation: Location? = locationResult.getLastLocation()
            if (mLastLocation!=null) {
                editor.putString("latitude", mLastLocation.latitude.toString())
                editor.putString("longitude", mLastLocation.longitude.toString())
                editor.apply()
            }
        }

    }





}