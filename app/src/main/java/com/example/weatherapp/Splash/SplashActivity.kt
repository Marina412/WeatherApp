package com.example.weatherapp.Splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.MainActivity
import com.example.weatherapp.Settings.View.FirstLocationDailog
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {


    lateinit var binding: ActivitySplashBinding

    lateinit var sharedPreference: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    private val parentJob = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

       sharedPreference = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
        editor=sharedPreference.edit()
        editor.putBoolean("isRegistered",false)
        editor.apply()



            val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)
            coroutineScope.launch {
                delay(4000)
                startMainActivity()
            }


    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onStop() {
        super.onStop()
        parentJob.cancel()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun navgation(isRegistered:Boolean){
        if (isRegistered==false){
            editor.putBoolean("isRegistered",true)
            editor.apply()
           FirstLocationDailog().show(supportFragmentManager,"FirstLocationDailog")
            Toast.makeText(this,"Welcome", Toast.LENGTH_SHORT).show()


        }
        else{

            var intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }

        }



}