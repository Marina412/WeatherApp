package com.example.weatherapp.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.MainActivity
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {


    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)


        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)

            var intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this@SplashActivity,"mmmmmmmmmmmmmmmmm", Toast.LENGTH_SHORT).show()


        }

    }


}