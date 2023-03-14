package com.example.weatherapp.Alerts.View

import android.content.Context
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentCustomDialogBinding

class CustomAlarmDialogFragment (private val context: Context, private val description: String){
  lateinit var binding:FragmentCustomDialogBinding


        private lateinit var dialog: View
        private var rington: MediaPlayer = MediaPlayer.create(context, R.raw.ring)

        fun onCreate() {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            dialog = inflater.inflate(R.layout.fragment_custom_dialog, null)
            binding = FragmentCustomDialogBinding.bind(dialog)

            binding.weatherNowtv.text = description
            binding.cancelBtn.setOnClickListener {
                try {
                    (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(dialog)
                    dialog.invalidate()
                    (dialog.parent as ViewGroup).removeAllViews()
                } catch (e: Exception) {
                }
                rington.release()
            }
            val LAYOUT_FLAG: Int = Flag()
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val layoutParams: WindowManager.LayoutParams = Params(LAYOUT_FLAG)
            windowManager.addView(dialog, layoutParams)
            rington.start()

        }

        private fun Flag(): Int {
            val LAYOUT_FLAG: Int
            LAYOUT_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            }
            else {WindowManager.LayoutParams.TYPE_PHONE}
            return LAYOUT_FLAG
        }

        private fun Params(LAYOUT_FLAG: Int): WindowManager.LayoutParams {
            val width = (context.resources.displayMetrics.widthPixels * 0.85).toInt()
            return WindowManager.LayoutParams(
                width,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
                PixelFormat.TRANSLUCENT
            )
        }


    }