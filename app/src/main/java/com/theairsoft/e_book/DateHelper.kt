package com.theairsoft.e_book

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    @JvmStatic
    val fullDateFormat = "yyyy-MM-dd HH:mm:ss"

    @JvmStatic
    var defaultDateFormat = fullDateFormat

    fun dateFormatter(myCalendar: Calendar): String {
        return java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG)
            .format(myCalendar.time)
    }

    @JvmOverloads
    fun str2Date(date: String, pattern: String = defaultDateFormat): Date {
        val format = SimpleDateFormat(pattern)

        return try {
            val d = format.parse(date)
            println(date)

            d
        } catch (e: Exception) {
            println("Parse error ")
            Date()
        }

    }

    fun convertStrDate(
        date: String,
        toPattern: String,
        fromPattern: String = defaultDateFormat,
    ): String {
        return date2Custom(str2Date(date, fromPattern), toPattern)
    }

    fun date2MonthAndDay(date: Date): String {
        return DateFormat.format("dd-MMM", date) as String
    }

    fun date2MonthAndDay(string: String): String {
        return date2MonthAndDay(str2Date(string))
    }

    fun date2MonthAndDayAndHour(date: Date): String {
        val sdf = SimpleDateFormat("dd-MMM, HH:mm")
        return sdf.format(date)
    }

    fun dateToFullStringFormat(calendar: Calendar): String {
        return dateToFullStringFormat(calendar.time)
    }


    fun date2Custom(date: Date, pattern: String): String {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(date)
    }


    fun dateToFullStringFormat(calendar: Date): String {
        return DateFormat.format(fullDateFormat, calendar.time).toString()
    }
}