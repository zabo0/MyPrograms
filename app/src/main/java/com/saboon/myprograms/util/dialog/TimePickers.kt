package com.saboon.myprograms.util.dialog

import android.content.Context
import android.text.format.DateFormat
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat

class TimePickers(private val context: Context, private val fragmentManager: FragmentManager) {

    private val isSystem24Hour = DateFormat.is24HourFormat(context)
    private val clockFormat = if(isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

    private var hour = 0
    private var minute = 0

    private lateinit var picker: MaterialTimePicker
    fun timePicker(title: String, callback:(String)->Unit){

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText(title)
            .setInputMode(INPUT_MODE_CLOCK)
            .build()


        picker.show(fragmentManager, "TAG")

        picker.addOnPositiveButtonClickListener{
            val hr = picker.hour
            val min = picker.minute
            val time = String.format("%02d:%02d", hr, min)
            callback(time)
        }
    }

    fun timePicker(title: String, timeText: String, callback:(String)->Unit){
        if (timeText != "") {
            hour = timeText.split(":")[0].toInt()
            minute = timeText.split(":")[1].toInt()
        }

        picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(hour)
            .setMinute(minute)
            .setTitleText(title)
            .setInputMode(INPUT_MODE_CLOCK)
            .build()

        picker.show(fragmentManager, "TAG")

        picker.addOnPositiveButtonClickListener{
            val hr = picker.hour
            val min = picker.minute
            val time = String.format("%02d:%02d", hr, min)
            callback(time)
        }
    }




}