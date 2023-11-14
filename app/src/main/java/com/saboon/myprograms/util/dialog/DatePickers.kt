package com.saboon.myprograms.util.dialog

import android.content.Context
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.saboon.myprograms.R
import com.saboon.myprograms.util.generator.DateTimeGenerator

class DatePickers(private val context: Context, private val fragmentManager: FragmentManager) {


    fun fullDatePicker(title: String, callback: (Long) -> Unit){
        val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(title)
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.show(fragmentManager,"TAG")

        datePicker.addOnPositiveButtonClickListener {
            callback(it)
        }
    }

    fun dayOfWeekPicker(v: View, callback: (String) -> Unit) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(R.menu.menu_event_dialog_popup, popup.menu)

        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.day_1 -> {
                    callback(v.resources.getString(R.string.monday))
                    true
                }
                R.id.day_2 -> {
                    callback(v.resources.getString(R.string.tuesday))
                    true
                }
                R.id.day_3 -> {
                    callback(v.resources.getString(R.string.wednesday))
                    true
                }
                R.id.day_4 -> {
                    callback(v.resources.getString(R.string.thursday))
                    true
                }
                R.id.day_5 -> {
                    callback(v.resources.getString(R.string.friday))
                    true
                }
                R.id.day_6 -> {
                    callback(v.resources.getString(R.string.saturday))
                    true
                }
                R.id.day_7 -> {
                    callback(v.resources.getString(R.string.sunday))
                    true
                }
                else -> {false}
            }
        }
        popup.show()
    }


}