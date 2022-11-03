package com.coxtunes.timetrackerapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.timetrackerapp.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    lateinit var binding: ActivityMain3Binding
    var seconds: Int = 0
    var running: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        startTimer()

        binding.buttonStartThird.setOnClickListener {
            running = true

        }

        binding.buttonStop.setOnClickListener {
            running = false
        }


        binding.buttonReset.setOnClickListener {
//            running = false
//            seconds = 0
            val hours = seconds / 3600
            val minute = (seconds % 3600) / 60
            val second = seconds % 60

            binding.textView3.text = second.toString()


        }
    }

    private fun startTimer() {
        val handler = Handler(Looper.getMainLooper())
        val runnable=object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minute = (seconds % 3600) / 60
                val second = seconds % 60

                val stringTimer = String.format("%02d,%02d,%02d", hours, minute, second)

                binding.counterTimerText.text = stringTimer
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable,0)
    }
}