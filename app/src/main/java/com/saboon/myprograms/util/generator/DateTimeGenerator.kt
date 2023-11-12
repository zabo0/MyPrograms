package com.saboon.myprograms.util.generator

import java.text.SimpleDateFormat
import java.util.Calendar

class DateTimeGenerator {

    fun getDateInMillis() = Calendar.getInstance().timeInMillis // get today in millisecond

    fun convertLongToDateTime(dateInMillis: Long, pattern: String): String {
        val c = Calendar.getInstance()
        c.timeInMillis = dateInMillis
        return SimpleDateFormat(pattern).format(c.time)
        //SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(Calendar.getInstance().time)
    }

    fun convertDateTimeToLong(dateTime: String, pattern: String): Long{
        return SimpleDateFormat(pattern).parse(dateTime).time
    }
}