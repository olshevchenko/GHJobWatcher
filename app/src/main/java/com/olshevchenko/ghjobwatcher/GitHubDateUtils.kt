package com.olshevchenko.ghjobwatcher

import android.util.Log
import org.apache.commons.lang3.time.DateUtils
import org.jetbrains.annotations.TestOnly
import java.text.SimpleDateFormat
import java.util.*

object GitHubDateUtils {
    /** Input date string format */
    private val jsonDatePattern = "EEE MMM dd HH:mm:ss 'UTC' yyyy" // "Tue Nov 24 19:04:39 UTC 2020"

    /** Output date string format */
    private val listDatePattern = "dd/MM/yyyy" // "31/12/2020"

    private var jsonDateFormat: SimpleDateFormat
    private var listDateFormat: SimpleDateFormat

    init {
        try {
            jsonDateFormat = SimpleDateFormat(jsonDatePattern, Locale.ENGLISH)
            listDateFormat = SimpleDateFormat(listDatePattern, Locale.ENGLISH)
        } catch (e: Exception) {
            Log.e("GitHubDateUtils", "SimpleDateFormat objects creation error => exit app")
            throw(e)
        }
    }

    /** Today's and yesterday's MIDNIGHT datetime */
    lateinit var todaysDate: Date
    lateinit var yesterdaysDate: Date

    /** Today's and yesterday's homemade date strings */
    private const val jobTodaysDateString = "Today"
    private const val jobYesterdaysDateString = "Yesterday"


    @JvmStatic
    fun cast2Midnight(date: Date): Date {
        val roundDate = DateUtils.round(date, Calendar.DAY_OF_MONTH)
        return DateUtils.addDays(roundDate, -1) // remove 1 EXTRA day
    }

    @JvmStatic
    fun parseFromJSONDate(string: String): Date =
            (jsonDateFormat.parse(string)) ?: Date()

//    @JvmStatic
    // test only
//    fun formatToJSONDate(date: Date): String = jsonDateFormat.format(date)

    @JvmStatic
    fun formatToListDate(date: Date): String = listDateFormat.format(date)

    /**
     * Converts creation MIDNIGHT date into suitable string form using today & yesterday Date values
     * @param mnDate is job creation date
     * @return 'today'| 'yesterday' | <listDateFormat> date for job creation date
     */
    @JvmStatic
    fun formatToHomemadeDate(mnDate: Date): String = when (mnDate) {
        todaysDate -> jobTodaysDateString
        yesterdaysDate -> jobYesterdaysDateString
        else -> formatToListDate(mnDate)
    }

    init {
        refreshDates()
    }

    /**
     * Refresh and cast datetimes (to midnight)
     */
    @JvmStatic
    @Synchronized
    fun refreshDates() {
        todaysDate = cast2Midnight(Date())
        yesterdaysDate = DateUtils.addDays(todaysDate, -1)
    }

}