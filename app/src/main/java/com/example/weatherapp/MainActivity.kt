package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.weatherapp.Alarts.View.AlertsFragment
import com.example.weatherapp.Fav.View.FavoriteFragment
import com.example.weatherapp.Fav.View.MapFragment
import com.example.weatherapp.Home.View.HomeFragment
import com.example.weatherapp.Settings.View.SettingsFragment
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

   // lateinit var binding: ActivityMainBinding
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
      // val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

//            binding = ActivityMainBinding.inflate(layoutInflater)
//            setContentView(binding.root)

            val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
            val navController: NavController? = findNavController()

       navController?.let {
           NavigationUI.setupWithNavController( bottomNavigationView,it)

       }

Toast.makeText(this,"bbbbbb",Toast.LENGTH_SHORT).show()

//       val customDialog=CustomDialog(this).customLocationDialog()//.showOptionDialog()
//        customDialog.show()
      /* navgation(HomeFragment())
            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId){
                    R.id.homeFragment->navgation(HomeFragment())
                    R.id.settingsFragment->navgation(SettingsFragment())
                    R.id.favoriteFragment->navgation(FavoriteFragment())
                    R.id.alertsFragment->navgation(AlertsFragment())

                }
                true
            }*/
    }

    private fun findNavController():NavController? {
        val navHostFragment = (this as? MainActivity)?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        return navHostFragment?.navController
    }

    fun navgation(fragment: Fragment){
        val fragmentManger=supportFragmentManager
        val fragmentTransition=fragmentManger.beginTransaction()
        fragmentTransition.replace(R.id.nav_host_fragment,fragment)
        fragmentTransition.commit()
    }

}