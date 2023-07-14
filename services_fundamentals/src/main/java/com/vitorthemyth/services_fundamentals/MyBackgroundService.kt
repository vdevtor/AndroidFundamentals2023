package com.vitorthemyth.services_fundamentals

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper


const val ACTION_COUNT = "com.example.ACTION_TASK_COMPLETED"

class MyBackgroundService : Service() {

    private val handler = Handler(Looper.getMainLooper())
    private var count = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        countUp()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        stopSelf()
    }

    private fun countUp() {
        handler.postDelayed({
            count++
            if (count < 20) {
                countUp()
            } else {
                // Notify MainActivity that the background task is completed
                val completionIntent = Intent(ACTION_COUNT)
                sendBroadcast(completionIntent)
                stopSelf()
            }
        }, 1000) // 1 second delay
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}