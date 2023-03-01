package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.Alarts.View.AlertsFragment
import com.example.weatherapp.Fav.View.FavoriteFragment
import com.example.weatherapp.Home.View.HomeFragment
import com.example.weatherapp.Home.ViewModel.HomeViewModel
import com.example.weatherapp.Home.ViewModel.HomeViewModelFactory
import com.example.weatherapp.Settings.View.SettingsFragment
import com.example.weatherapp.Splash.SplashFragment
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            binding = ActivityMainBinding.inflate(layoutInflater)

            setContentView(binding.root)



//       val customDialog=CustomDialog(this).customLocationDialog()//.showOptionDialog()
//        customDialog.show()
            navgation(SplashFragment())
            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home->navgation(HomeFragment())
                    R.id.settings->navgation(SettingsFragment())
                    R.id.favorite->navgation(FavoriteFragment())
                    R.id.alerts->navgation(AlertsFragment())

                }
                true
            }
    }

    fun navgation(fragment: Fragment){
        val fragmentManger=supportFragmentManager
        val fragmentTransition=fragmentManger.beginTransaction()
        fragmentTransition.replace(R.id.nav_host_fragment,fragment)
        fragmentTransition.commit()
    }

}