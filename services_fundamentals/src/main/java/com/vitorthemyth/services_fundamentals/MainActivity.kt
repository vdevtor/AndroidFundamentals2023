package com.vitorthemyth.services_fundamentals

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.vitorthemyth.services_fundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    0
            )
        }

        initViews()
        registerFilter()
        startBackgroundService()
    }

    private fun registerFilter() {
        // Register the BroadcastReceiver
        val filter = IntentFilter(ACTION_COUNT)
        registerReceiver(taskCompletedReceiver, filter)
    }

    private fun startBackgroundService() {
        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        startService(serviceIntent)
    }


    private val taskCompletedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == ACTION_COUNT) {
                    Toast.makeText(this@MainActivity, "action completed", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initViews(){
        binding.btnStart.setOnClickListener {
            Intent(this,MyForegroundService::class.java).also {
                it.action = MyForegroundService.ForegroundAction.START.toString()
                startService(it)
            }
        }

        binding.btnStop.setOnClickListener {
            Intent(this,MyForegroundService::class.java).also {
                it.action = MyForegroundService.ForegroundAction.STOP.toString()
                startService(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the BroadcastReceiver
        unregisterReceiver(taskCompletedReceiver)
    }
}