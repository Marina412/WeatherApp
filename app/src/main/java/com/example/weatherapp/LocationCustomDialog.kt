package com.example.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class LocationCustomDialog(var context: Context) {
    val items = arrayOf<CharSequence>(
        context.getResources().getString(R.string.GPS),
        context.getResources().getString(R.string.Map)
    )
    var selected_item:String?=null


    @SuppressLint("ResourceType", "SuspiciousIndentation")
    fun customLocationDialog(): AlertDialog {
        var alertDialog: AlertDialog.Builder= AlertDialog.Builder(context).apply {
            setTitle(context.getResources().getString(R.string.LocationMode))

            setCancelable(false)
            setIcon(context.getResources().getDrawable(R.drawable.baseline_map_24))
            setSingleChoiceItems(items,-1){
                    dialog, which ->
                selected_item= items[which].toString()
                when (which) {

                    0 -> { Toast.makeText(context,selected_item,Toast.LENGTH_SHORT).show() }
                    1 -> { Toast.makeText(context,selected_item,Toast.LENGTH_SHORT).show() }
                }

            }
            setPositiveButton(context.getResources().getString(R.string.ok)) {
                    dialog, which ->
                if (selected_item==null) {
                    setCancelable(false)
                }
                else {
                    dosomething()
                    dialog.cancel()
                    dialog.dismiss()
                }

            }
         }
        val d=alertDialog.create()
            d.window?.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.gradient_nav_view_background))


        return d

    }

    private fun dosomething() {
        Toast.makeText(context,selected_item,Toast.LENGTH_SHORT).show()
    }

}