package com.vitorthemyth.androidfundamentals2023

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyTargetService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Put the logic for your service here
        Log.d("MyApp", "onStartCommand: initialized from A ")
        return START_STICKY
    }
}
