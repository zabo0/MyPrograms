package com.saboon.myprograms.util.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saboon.myprograms.R
import java.util.Calendar

class DatePickers(private val context: Context, private val fragmentManager: FragmentManager) {

    private val calendar = Calendar.getInstance()

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

    fun dayOfWeekPicker(v: View, callback: (Long) -> Unit) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(R.menu.menu_event_dialog_popup, popup.menu)

        popup.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.day_1 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                R.id.day_2 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                R.id.day_3 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                R.id.day_4 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                R.id.day_5 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                R.id.day_6 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                R.id.day_7 -> {
                    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
                    callback(calendar.timeInMillis)
                    true
                }
                else -> {false}
            }
        }
        popup.show()
    }


    fun dayOfMonthPicker(callback: (Long) -> Unit){
        val numberPickerLayout = LayoutInflater.from(context).inflate(R.layout.dialog_day_of_month_picker, null)
        val numberPicker: NumberPicker = numberPickerLayout.findViewById(R.id.day_of_month_picker)
        val saveButton: Button = numberPickerLayout.findViewById(R.id.dialogMonthPickerSaveButton)
        val cancelButton: Button = numberPickerLayout.findViewById(R.id.dialogMonthPickerCancelButton)

        numberPicker.maxValue = 31
        numberPicker.minValue = 1

        val numberPickerAlertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(context.resources.getString(R.string.selectDay))
            .create()



        saveButton.setOnClickListener {
            calendar.set(Calendar.DAY_OF_MONTH, numberPicker.value)
            callback(calendar.timeInMillis)
            numberPickerAlertDialog.cancel()
        }

        cancelButton.setOnClickListener {
            numberPickerAlertDialog.cancel()
        }

        numberPickerAlertDialog.setView(numberPickerLayout)
        numberPickerAlertDialog.show()

    }

    fun dayOfYearPicker(callback: (Long) -> Unit){
        val numberPickerLayout = LayoutInflater.from(context).inflate(R.layout.dialog_day_of_year_picker, null)
        val dayPicker: NumberPicker = numberPickerLayout.findViewById(R.id.day_picker)
        val monthPicker: NumberPicker = numberPickerLayout.findViewById(R.id.month_picker)
        val saveButton: Button = numberPickerLayout.findViewById(R.id.dialogPickerSaveButton)
        val cancelButton: Button = numberPickerLayout.findViewById(R.id.dialogPickerCancelButton)

        dayPicker.maxValue = 31
        dayPicker.minValue = 1

        monthPicker.maxValue = 11
        monthPicker.minValue = 0

        val months = arrayOf<String>(
            numberPickerLayout.resources.getStringArray(R.array.months)[0],
            numberPickerLayout.resources.getStringArray(R.array.months)[1],
            numberPickerLayout.resources.getStringArray(R.array.months)[2],
            numberPickerLayout.resources.getStringArray(R.array.months)[3],
            numberPickerLayout.resources.getStringArray(R.array.months)[4],
            numberPickerLayout.resources.getStringArray(R.array.months)[5],
            numberPickerLayout.resources.getStringArray(R.array.months)[6],
            numberPickerLayout.resources.getStringArray(R.array.months)[7],
            numberPickerLayout.resources.getStringArray(R.array.months)[8],
            numberPickerLayout.resources.getStringArray(R.array.months)[9],
            numberPickerLayout.resources.getStringArray(R.array.months)[10],
            numberPickerLayout.resources.getStringArray(R.array.months)[11],
        )
        monthPicker.displayedValues = months

        val numberPickerAlertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(context.resources.getString(R.string.selectDay))
            .create()



        saveButton.setOnClickListener {
            calendar.set(Calendar.DAY_OF_MONTH, dayPicker.value)
            calendar.set(Calendar.MONTH, monthPicker.value)
            callback(calendar.timeInMillis)
            numberPickerAlertDialog.cancel()
        }

        cancelButton.setOnClickListener {
            numberPickerAlertDialog.cancel()
        }

        numberPickerAlertDialog.setView(numberPickerLayout)
        numberPickerAlertDialog.show()
    }


}