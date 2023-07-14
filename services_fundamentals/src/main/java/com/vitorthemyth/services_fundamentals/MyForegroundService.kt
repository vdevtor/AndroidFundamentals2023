package com.vitorthemyth.services_fundamentals

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat


class MyForegroundService : Service() {


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ForegroundAction.START.toString() -> start()
            ForegroundAction.STOP.toString() -> stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }


    private fun start() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val stopIntent = Intent(this, MyForegroundService::class.java).apply {
            action = ForegroundAction.STOP.toString()
        }
        val stopPendingIntent = PendingIntent.getService(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My Running service")
                .setContentText("Running on foreground")
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.close_btn, "Stop Service", stopPendingIntent)
                .build()

        startForeground(1,notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    enum class ForegroundAction {
        START, STOP
    }


}