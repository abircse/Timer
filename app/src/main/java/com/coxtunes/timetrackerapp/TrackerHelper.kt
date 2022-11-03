package com.coxtunes.timetrackerapp

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class TrackerHelper(context: Context) {

    private var sharedPreference: SharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
    private var dateFormat = SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault())
    private var timeCounting = false
    private var startTime: Date? = null
    private var stopTime: Date? = null

    init {
        timeCounting = sharedPreference.getBoolean(COUNTING_KEY, false)
        val startString = sharedPreference.getString(START_TIME, null)
        if (startString !=  null){
            startTime = dateFormat.parse(startString)
        }
        val stopString = sharedPreference.getString(STOP_TIME, null)
        if (stopString != null){
            stopTime = dateFormat.parse(stopString)
        }
    }

    fun startTime(): Date? = startTime

    fun setStartTime(date: Date?) {
        startTime = date
        with(sharedPreference.edit()) {
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(START_TIME, stringDate)
            apply()
        }
    }

    fun stopTime(): Date? = stopTime

    fun setStopTime(date: Date?) {
        stopTime = date
        with(sharedPreference.edit()) {
            val stringDate = if (date == null) null else dateFormat.format(date)
            putString(STOP_TIME, stringDate)
            apply()
        }
    }

    fun timerCounting(): Boolean = timeCounting

    fun setTimerCounting(value: Boolean) {
        timeCounting = value
        with(sharedPreference.edit()) {
            putBoolean(COUNTING_KEY, value)
            apply()
        }
    }

    companion object {
        const val PREFERENCE = "preference"
        const val START_TIME = "startTimeKey"
        const val STOP_TIME = "stopTimeKey"
        const val COUNTING_KEY = "countingKey"
    }

}