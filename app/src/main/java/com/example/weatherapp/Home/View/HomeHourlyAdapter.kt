package com.example.weatherapp.Home.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.HourlyRowBinding
import com.example.weatherapp.models.Hourly


class HomeHourlyAdapter(
    val hourlyWeatherList: List<Hourly>,
    val timeZone:String,
    val tempUnit:String
) :
    RecyclerView.Adapter<HomeHourlyAdapter.Holder>() {
    lateinit var binding: HourlyRowBinding
     class Holder(var bindingHolder: HourlyRowBinding) : RecyclerView.ViewHolder(bindingHolder.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater:LayoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= HourlyRowBinding.inflate(inflater,parent,false)
        return Holder(binding)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val hourlyWeatherItem:Hourly=hourlyWeatherList[position]
        holder.bindingHolder.hourlyTime.text=UtilsFunction.getCurrentTime(hourlyWeatherItem.dt, timezone =timeZone )


        //holder.bindingHolder.hourlyTimelyTemp.text=hourlyWeatherItem.temp.toString()


        when (tempUnit){
           Constants.FAHRENHEIT  ->holder.bindingHolder.hourlyTimelyTemp.text=
                hourlyWeatherItem.temp.toString()+"°F"

           Constants.CELSIUS->holder.bindingHolder.hourlyTimelyTemp.text=
                UtilsFunction.convertFromKelvinToCelsius(hourlyWeatherItem.temp)+"°C"

           Constants.KELVIN->holder.bindingHolder.hourlyTimelyTemp.text=
                UtilsFunction.convertFromKelvinToFahrenheit(hourlyWeatherItem.temp)+"°K"
        }


        holder.bindingHolder.wetherHourlyDesc.text=hourlyWeatherItem.weather[0].description
        Glide.with(holder.bindingHolder.wetherHourlyImagDesc.context).load(UtilsFunction.getWeatherIcon( hourlyWeatherItem.weather[0].icon)).into(holder.bindingHolder.wetherHourlyImagDesc)


    }

    override fun getItemCount(): Int =hourlyWeatherList.size

}


