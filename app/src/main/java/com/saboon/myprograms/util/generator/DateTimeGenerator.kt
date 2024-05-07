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

    fun convertTimeToLong(time: String, pattern: String): Long{
        if (time != ""){
            return SimpleDateFormat(pattern).parse(time).time
        }
        return 0
    }

    fun convertDateToLong(date: String, pattern: String): Long{
        if (date != ""){
            return SimpleDateFormat(pattern).parse(date).time
        }
        return 0
    }

    fun getWeekNumber():Int{
        return SimpleDateFormat("ww").format(getDateInMillis()).toInt()
    }

    fun getWeekNumber(date: Long): Int{
        val c = Calendar.getInstance()
        c.timeInMillis = date
        return c.weekYear
        //return SimpleDateFormat("ww").format(c.timeInMillis).toInt()
    }
}