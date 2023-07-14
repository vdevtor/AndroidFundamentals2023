package com.vitorthemyth.services_fundamentals

import android.content.*
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.vitorthemyth.services_fundamentals.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var boundService: MyBoundService? = null
    private var isBound: Boolean = false

    private val connection: ServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBoundService.MyBinder
            boundService = binder.getService()
            isBound = true

            // Now you can call methods on the boundService
            // Handle the result as needed
            val result = boundService?.doSomething()
            binding.btnStart.text = result

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }


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
        bindService()
    }

    private fun bindService() {
        Handler(Looper.getMainLooper()).postDelayed({
            val serviceIntent = Intent(this, MyBoundService::class.java)
            bindService(serviceIntent, connection, BIND_AUTO_CREATE)
        },5000)
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

        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}