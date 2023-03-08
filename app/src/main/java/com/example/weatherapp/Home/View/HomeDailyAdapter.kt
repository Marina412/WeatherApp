package com.example.weatherapp.Home.View

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.DailyRowBinding
import com.example.weatherapp.models.Daily


class HomeDailyAdapter(
    val dailyWeatherList: List<Daily>,
    val timeZone:String

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
        val dailyWeatherItem:Daily=dailyWeatherList[position]

        holder.bindingHolder.dayName.text=UtilsFunction.getDay(dailyWeatherItem.dt, timezone = timeZone)
        holder.bindingHolder.tvDayTemp.text=dailyWeatherItem.temp.max.toString()

/*
        holder.bindingHolder.tvDayHumidity.text= dailyWeatherItem.humidity.toString()
            holder.bindingHolder.tvDayPressure.text=dailyWeatherItem.pressure.toString()
            holder.bindingHolder.tvDayWindSpeed.text=dailyWeatherItem.windSpeed.toString()
            holder.bindingHolder.tvDayCloud.text=dailyWeatherItem.clouds.toString()*/
        holder.bindingHolder.wetherDalyDesc.text=dailyWeatherItem.weather[0].description
        Glide.with(holder.bindingHolder.wetherDaylyImagDesc.context).load(UtilsFunction.getWeatherIcon(dailyWeatherItem.weather[0].icon)).into(holder.bindingHolder.wetherDaylyImagDesc)
    }

    override fun getItemCount():Int=dailyWeatherList.size


}


