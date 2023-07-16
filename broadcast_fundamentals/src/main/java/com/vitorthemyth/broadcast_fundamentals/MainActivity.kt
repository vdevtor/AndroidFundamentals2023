package com.vitorthemyth.broadcast_fundamentals

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vitorthemyth.broadcast_fundamentals.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver : MyBroadCastReceiver
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun registerBroadCast() {
        broadcastReceiver = MyBroadCastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_CUSTOM_BROADCAST)
        registerReceiver(broadcastReceiver, intentFilter)
    }


    private fun initViews(){
        binding.btnBroadCastMessage.setOnClickListener {
            sendBroadCast()
        }
    }


    private fun sendBroadCast(){
        val intent = Intent(ACTION_CUSTOM_BROADCAST)
        intent.putExtra("message", "Hello, this is a custom broadcast message!")
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
       unregisterReceiver(broadcastReceiver)
    }

    companion object {
        const val ACTION_CUSTOM_BROADCAST = "com.vitorthemyth.broadcast_fundamentals.CUSTOM_BROADCAST"
    }
}