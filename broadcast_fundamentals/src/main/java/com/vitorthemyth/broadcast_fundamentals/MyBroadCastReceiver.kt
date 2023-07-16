package com.vitorthemyth.broadcast_fundamentals

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == MainActivity.ACTION_CUSTOM_BROADCAST) {
            val message = intent.getStringExtra("message")
            Toast.makeText(context, "Received: $message", Toast.LENGTH_SHORT).show()
        }
    }
}