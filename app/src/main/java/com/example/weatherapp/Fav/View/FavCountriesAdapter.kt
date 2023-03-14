package com.example.weatherapp.Fav.View

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Model.MapModel
import com.example.weatherapp.R
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.FavRowBinding

class FavCountriesAdapter (
val favCountriesList:List<MapModel>,
val listener: FavOnClickListener
        ) :
    RecyclerView.Adapter<FavCountriesAdapter.Holder>() {
    lateinit var binding: FavRowBinding

    class Holder(var bindingHolder: FavRowBinding) : RecyclerView.ViewHolder(bindingHolder.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= FavRowBinding.inflate(inflater,parent,false)
        return Holder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FavCountriesAdapter.Holder, position: Int) {
        val favCountrieItem: MapModel = favCountriesList[position]

        holder.bindingHolder.favCuntrytv.text="${favCountrieItem.countryName}  ,  ${favCountrieItem.adminArea}"

        binding.deleteFavIcon.setOnClickListener {
            listener.onDeleteClick(favCountrieItem)

        }

       binding.favCardView.setOnClickListener {
           if(UtilsFunction.isOnline(holder.bindingHolder.favCardView.context))
           {
               listener.onFavClick(favCountrieItem)
           }

          else
          {
            Toast.makeText(holder.bindingHolder.favCardView.context,R.string.offline_mode,Toast.LENGTH_LONG).show()
          }
       }




    }

    override fun getItemCount():Int=favCountriesList.size


}