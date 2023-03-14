package com.example.weatherapp.Alerts.View

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Model.RoomAlertsModel
import com.example.weatherapp.Utils.UtilsFunction
import com.example.weatherapp.databinding.AlertsRowBinding

class AlertsAdapter(
val alertseList:List<RoomAlertsModel>,
val listener: AlertsOnClickLisener
) :
RecyclerView.Adapter<AlertsAdapter.Holder>() {
    lateinit var binding: AlertsRowBinding
    class Holder(var bindingHolder: AlertsRowBinding) : RecyclerView.ViewHolder(bindingHolder.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsAdapter.Holder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding= AlertsRowBinding.inflate(inflater,parent,false)
        return AlertsAdapter.Holder(binding)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AlertsAdapter.Holder, position: Int) {
        val alertItem: RoomAlertsModel = alertseList[position]

        holder.bindingHolder.endDatetv.text=alertItem.endDate
        holder.bindingHolder.startDatetv.text= "" +alertItem.startDay + '/' + alertItem.startMonth + '/' + alertItem.startYear

        //holder.bindingHolder.startDatetv.text= alertItem.startDate
        holder.bindingHolder.timtv.text=alertItem.time.toString()
        holder.bindingHolder.alertLocationtv.text=UtilsFunction.getFullAddress(alertItem.lat,alertItem.lon,holder.bindingHolder.favCardView.context)
        binding.deleteAlertIcon.setOnClickListener {
            listener.onDeleteClick(alertItem)
        }
    }
    override fun getItemCount():Int=alertseList.size
}