package com.example.weatherapp.Home.View

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.Model.Daily
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.DailyRowBinding


class HomeDailyAdapter(
    val dailyWeatherList: List<Daily>,
    val timeZone:String,
    val tempUnit:String,
    val lang:String


) :
    RecyclerView.Adapter<HomeDailyAdapter.Holder>() {
    lateinit var binding: DailyRowBinding

     class Holder(var bindingHolder: DailyRowBinding) : RecyclerView.ViewHolder(bindingHolder.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater:LayoutInflater=parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= DailyRowBinding.inflate(inflater,parent,false)
        return Holder(binding)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val dailyWeatherItem: Daily =dailyWeatherList[position]

        when(lang){
            Constants.ENGLISH->
                holder.bindingHolder.dayName.text=UtilsFunction.getDay(dailyWeatherItem.dt, timezone = timeZone)

            Constants.ARABIC->
                holder.bindingHolder.dayName.text=UtilsFunction.getArabicDayName(
                    UtilsFunction.getDay(dailyWeatherItem.dt, timezone = timeZone))

            else->holder.bindingHolder.dayName.text=UtilsFunction.getDay(dailyWeatherItem.dt, timezone = timeZone)

        }



        when (tempUnit){
            Constants.FAHRENHEIT  ->holder.bindingHolder.tvDayTemp.text=
                dailyWeatherItem.temp.max.toString()+"/"+dailyWeatherItem.temp.min.toString()+"°F"

            Constants.CELSIUS->holder.bindingHolder.tvDayTemp.text=
                UtilsFunction.convertFromKelvinToCelsius(dailyWeatherItem.temp.max)+
                        "/"+UtilsFunction.convertFromKelvinToCelsius(dailyWeatherItem.temp.min)+"°C"

            Constants.KELVIN->holder.bindingHolder.tvDayTemp.text=
                UtilsFunction.convertFromKelvinToFahrenheit(dailyWeatherItem.temp.max)+
                        "/"+UtilsFunction.convertFromKelvinToFahrenheit(dailyWeatherItem.temp.min)+"°K"

            else->holder.bindingHolder.tvDayTemp.text=
                UtilsFunction.convertFromKelvinToCelsius(dailyWeatherItem.temp.max)+
                        "/"+UtilsFunction.convertFromKelvinToCelsius(dailyWeatherItem.temp.min)+"°C"

        }


        holder.bindingHolder.wetherDalyDesc.text=dailyWeatherItem.weather[0].description
        Glide.with(holder.bindingHolder.wetherDaylyImagDesc.context).load(UtilsFunction.getWeatherIcon(dailyWeatherItem.weather[0].icon)).into(holder.bindingHolder.wetherDaylyImagDesc)


    }

    override fun getItemCount():Int=dailyWeatherList.size


}


