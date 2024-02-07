package com.tutorial.cronometer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tutorial.cronometer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var foregroundServiceIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foregroundServiceIntent = Intent(this, MyService::class.java)

        binding.btnStart.setOnClickListener {
            startService(foregroundServiceIntent)
        }
        binding.btnStop.setOnClickListener {
            stopService(foregroundServiceIntent)
        }
    }
}


