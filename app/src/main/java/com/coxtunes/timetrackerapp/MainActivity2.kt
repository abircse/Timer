package com.coxtunes.timetrackerapp

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.coxtunes.timetrackerapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    lateinit var binding: ActivityMain2Binding
    private var pauseAt: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            binding.cronometer.base = SystemClock.elapsedRealtime() - pauseAt
            binding.cronometer.start()
        }

        binding.pauseBtn.setOnClickListener {
            pauseAt = SystemClock.elapsedRealtime() - binding.cronometer.base
            binding.cronometer.stop()
            val hours = (pauseAt / 3600000)
            val minutes = (pauseAt - hours * 3600000).toInt() / 60000
            val seconds = (pauseAt - hours * 3600000 - minutes * 60000).toInt() / 1000

            binding.textView.text = "Hour: $hours || Minutes: $minutes || Second: $seconds"
        }

    }
}