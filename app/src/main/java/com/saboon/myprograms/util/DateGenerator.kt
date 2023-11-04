package com.saboon.myprograms.util

import java.text.SimpleDateFormat
import java.util.Calendar

class DateGenerator {

    fun getDateInMillis() = Calendar.getInstance().timeInMillis // get today in millisecond

    fun convertLongDateToString(dateInMillis: Long, pattern: String): String {
        val c = Calendar.getInstance()
        c.timeInMillis = dateInMillis
        return SimpleDateFormat(pattern).format(c.time)
        //SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
    }
}