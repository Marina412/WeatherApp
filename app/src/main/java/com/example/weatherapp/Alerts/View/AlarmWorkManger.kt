package com.example.weatherapp.Alerts.View

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherapp.Data.Repository
import com.example.weatherapp.R
import com.example.weatherapp.Utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class AlarmWorkManger(var context: Context,var paras: WorkerParameters): CoroutineWorker(context,paras) {
    private lateinit var repository: Repository
    val endDate = inputData.getLong("endDate" , 0)
    val date=Calendar.getInstance().timeInMillis
    private var lat:Double=0.0
    private var long:Double=0.0
    lateinit var  alertDescription: String

    lateinit var sharedPreference: SharedPreferences

    override suspend fun doWork(): Result {
        if(date>endDate) {
            repository = Repository.getInstance(context as Application)
            sharedPreference =
                context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

            lat = sharedPreference.getString("latitude", "")!!.toDouble()
            long = sharedPreference.getString("longitude", "")!!.toDouble()

            var alarmResponse = repository.getCurrentWeatherForWorker(lat, long)

            if (!alarmResponse.alerts.isEmpty())
            {
                alertDescription=alarmResponse.alerts.get(0).description
            }
            else{
                alertDescription=context.getString( R.string.alert_notfication_message ) + alarmResponse.current?.weather?.get(0)?.description ?: ""
            }
//todo nameing
            when (sharedPreference.getString("notification", Constants.NOTIFICATIONS)) {
                (Constants.ALERTS) -> {
                    //setAlarm(context, "Weather App", alertDescription)
                    GlobalScope.launch(Dispatchers.Main) {
                        CustomAlarmDialogFragment(context, alertDescription).onCreate()
                    }
                }
                else -> {
                    setNotification(context, "SKYOURS", alertDescription)
                }
            }
            return Result.success()
        }
        else{
            return Result.failure()
        }

    }

    private fun setNotification(context: Context, title: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.sun_logo)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }

    }
}
