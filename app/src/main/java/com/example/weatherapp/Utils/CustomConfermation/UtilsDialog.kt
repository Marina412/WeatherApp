package com.example.weatherapp.Utils.CustomConfermation

import android.app.AlertDialog
import android.content.Context
import com.example.weatherapp.R

interface UtilsDialog {
    companion object{


        fun showDialog(title:String, message:String, alertButtonResult: AlertButtonResult, context: Context) {
            val alertBuild: AlertDialog.Builder = AlertDialog.Builder(context)
            alertBuild.setTitle(title)
            alertBuild.setMessage(message)
            alertBuild.setPositiveButton(R.string.ok) {
                    _, _ ->
                alertButtonResult.IfOk()
            }
            alertBuild.setNegativeButton(R.string.cancel) {
                    _, _ ->
                alertButtonResult.IfCancel()
            }
            val alert = alertBuild.create()
            alert.show()
        }

    }
}