package com.vitorthemyth.androidfundamentals2023

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    companion object {
        const val ACTION_CUSTOM_BROADCAST = "com.vitorthemyth.broadcast_fundamentals.CUSTOM_BROADCAST"
    }

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == ACTION_CUSTOM_BROADCAST) {
                    val message = intent.getStringExtra("message")
                    Toast.makeText(context, "Received from another app: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val intentFilter = IntentFilter(ACTION_CUSTOM_BROADCAST)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

}