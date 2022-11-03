package com.coxtunes.timetrackerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.timetrackerapp.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var trackerHelper: TrackerHelper
    private val timer = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        trackerHelper = TrackerHelper(applicationContext)

        binding.buttonStartTime.setOnClickListener {
            startStopAction()
        }
        binding.buttonEndTime.setOnClickListener {
            resetAction()
        }


        if (trackerHelper.timerCounting()){
            startTimer()
        }
        else{
            stopTimer()
            if (trackerHelper.startTime() != null && trackerHelper.stopTime() != null){
                val time = Date().time - calculateRestartTime()!!.time
                binding.tvTextTimeView.text = timeStringFromLong(time)
            }
        }

            timer.scheduleAtFixedRate(TimeTask(), 0, 500)
    }

    private inner class TimeTask: TimerTask(){
        override fun run() {
            if (trackerHelper.timerCounting()){
                val time = Date().time - trackerHelper.startTime()!!.time
                binding.tvTextTimeView.text = timeStringFromLong(time)
            }
        }

    }

    private fun resetAction() {
        trackerHelper.setStopTime(null)
        trackerHelper.setStartTime(null)
        stopTimer()
        binding.tvTextTimeView.text = timeStringFromLong(0)
    }

    private fun timeStringFromLong(ms: Long): String? {
        val seconds = (ms / 1000) % 60
        val minutes = (ms / 1000 * 60) % 60
        val hours = ((ms / 1000 * 60 * 60) % 24)

        return makeTimeString(hours,minutes,seconds)

    }


    private fun startStopAction(){
        if (trackerHelper.timerCounting()){
            trackerHelper.setStopTime(Date())
            stopTimer()
        }
        else{
            if (trackerHelper.stopTime() != null){
                trackerHelper.setStartTime(calculateRestartTime())
                trackerHelper.setStopTime(null)
            }
            else
            {
                trackerHelper.setStartTime(Date())
            }
            startTimer()
        }
    }

    private fun makeTimeString(hours: Long, minutes: Long, seconds: Long): String? {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun stopTimer() {
        trackerHelper.setTimerCounting(false)
        binding.buttonStartTime.text = "Start"
    }

    private fun startTimer() {
        trackerHelper.setTimerCounting(true)
        binding.buttonStartTime.text = "Stop"
    }

    private fun calculateRestartTime(): Date? {
        val diff = trackerHelper.startTime()!!.time - trackerHelper.stopTime()!!.time
        return Date(System.currentTimeMillis() + diff)
    }

}