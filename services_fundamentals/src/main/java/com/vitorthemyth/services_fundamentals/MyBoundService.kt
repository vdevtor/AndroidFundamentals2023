package com.vitorthemyth.services_fundamentals

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyBoundService : Service() {

    // Binder instance to return to clients
    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService {
            // Return this instance of MyBoundService so clients can access public methods
            return this@MyBoundService
        }
    }

    // Public method to be accessed by clients
    fun doSomething(): String {
        return "Hello from MyBoundService!"
    }

    override fun onBind(intent: Intent): IBinder {
        // Return the binder instance when clients bind to the service
        return binder
    }


}
