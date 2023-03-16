package com.example.weatherapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

       lateinit var binding: ActivityMainBinding


    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

           sharedPreference = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
           editor=sharedPreference.edit()


            val isRegistered=sharedPreference.getBoolean("isRegistered",false)


            val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView
            val navController: NavController? = findNavController()

       navController?.let {
           NavigationUI.setupWithNavController( bottomNavigationView,it)

       }
            navController?.navigate(R.id.blankFragment)
            if (isRegistered == false) {
                editor.putBoolean("isRegistered", true)
                editor.apply()
                navController?.navigate(R.id.firstLocationDailog)
                Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_SHORT).show()

            } else {
                    if(UtilsFunction.isOnline(this)){
                        Toast.makeText(this, getString(R.string.welcome_back), Toast.LENGTH_SHORT).show()
                    }
                    else{

                        Toast.makeText(this, getString(R.string.welcome_back)+getString(R.string.offline_mode), Toast.LENGTH_SHORT).show()
                    }

                navController?.navigate(R.id.homeFragment)
            }
        }
    private fun findNavController():NavController? {
        val navHostFragment = (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        return navHostFragment?.navController
    }




}