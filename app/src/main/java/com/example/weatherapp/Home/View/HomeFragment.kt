package com.example.weatherapp.Home.View

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Home.ViewModel.HomeViewModel
import com.example.weatherapp.Home.ViewModel.HomeViewModelFactory
import com.example.weatherapp.LocalDatabase.ConcreteLocalSource
import com.example.weatherapp.Model.Repository
import com.example.weatherapp.Model.RoomWeatherModel
import com.example.weatherapp.Networking.APIClient
import com.example.weatherapp.Utils.ApiStateWeather
import com.example.weatherapp.Utils.Constants
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.models.Current
import com.example.weatherapp.models.Daily
import com.example.weatherapp.models.Hourly

private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {
    lateinit var viewModel: HomeViewModel
    lateinit var hViewModelFactory: HomeViewModelFactory
    lateinit var repository: Repository

    lateinit var binding:FragmentHomeBinding

    lateinit var sharedPreferences: SharedPreferences


    lateinit var tempUnit:String
    lateinit var windSpeedUnit:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding= FragmentHomeBinding.inflate(inflater,container,false)

        sharedPreferences = requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

        tempUnit= sharedPreferences.getString("tempUnit","")!!
        windSpeedUnit= sharedPreferences.getString("windUnit","")!!

        val localSource = ConcreteLocalSource(requireContext())
        val remoteSource=APIClient.getInstane()

        repository =  Repository.getInstance(localSource,remoteSource,sharedPreferences)



        hViewModelFactory = HomeViewModelFactory(repository)
        viewModel =ViewModelProvider(this, hViewModelFactory).get(HomeViewModel::class.java)




        lifecycleScope.launchWhenCreated { 
            viewModel.data.collect{
                when(it){
                    is ApiStateWeather.Success ->{

                        Log.i(TAG, "onCreateView:  ${it.data.timezone}")
                        //   successDailyData(it.data.daily.map {it.copy(temp = UtilsFunction.convertFromKelvinToFahrenheit()},it.data.timezone)
                        successDailyData(it.data.daily,it.data.timezone,tempUnit)
                        successHourlyData(it.data.hourly,it.data.timezone,tempUnit)
                       successCurrentData(it.data.current)

                       viewModel.insertLastResponse(RoomWeatherModel(wether = it.data))

                    }
                    is ApiStateWeather.Failure -> {
                        Toast.makeText(requireContext(), "${it.msg}", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.i(TAG, "onCreateView: else")
                    }
                }
            }
        }


        return binding.root
    }


    fun successDailyData(data:List<Daily>,timeZone:String,tempUnit:String){

        binding.recycleDay.adapter =HomeDailyAdapter(data,timeZone,tempUnit)
        binding.recycleDay.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleDay.apply {

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
            }
        }
    }

    fun successCurrentData(data:Current){
        when (tempUnit){
            Constants.FAHRENHEIT  ->binding.currentTemperature.text=
                data.temp.toString()+"°F"

            Constants.CELSIUS->binding.currentTemperature.text=
                UtilsFunction.convertFromKelvinToCelsius(data.temp)+"°C"

            Constants.KELVIN->binding.currentTemperature.text=
                UtilsFunction.convertFromKelvinToFahrenheit(data.temp)+"°K"
        }
        binding.wetherCurrentDesc.text=data.weather[0].description
       // binding.currentTemperature.text=data.temp.toString()
        binding.currentWeatherIcon.setImageResource(UtilsFunction.getWeatherIcon(data.weather[0].icon))
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        binding.tvHumidity.text=data.humidity.toString()
        binding.tvCloud.text=data.clouds.toString()
        binding.tvPressure.text=data.pressure.toString()
        binding.tvWind.text=data.windDeg.toString()
        binding.tvVisibility.text=data.visibility.toString()
        when (windSpeedUnit){
            Constants.MILESHOUR  ->
                binding.tvWindSpeed.text=
                    UtilsFunction.convertMeterspersecToMilesperhour(data.windSpeed).toString()

            Constants.METERSEC->
                binding.tvWindSpeed.text=
                    data.windSpeed.toString()
        }
        //binding.tvWindSpeed.text=data.windSpeed.toString()

    }

    fun successHourlyData(data:List<Hourly>,timezone:String,tempUnit:String){

        binding.recycleHourly.adapter =HomeHourlyAdapter(data,timezone, tempUnit)
        binding.recycleHourly.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleHourly.apply {

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.HORIZONTAL
            }
        }

    }

}