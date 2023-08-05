package com.vitorthemyth.audiorecorder2

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.vitorthemyth.audiorecorder2.playback.AndroidAudioPlayer
import com.vitorthemyth.audiorecorder2.recorder.AndroidAudioRecorder
import com.vitorthemyth.audiorecorder2.ui.theme.AndroidFundamentals2023Theme
import java.io.File

class MainActivity : ComponentActivity() {
    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    private var audioFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )

        setContent {
            AndroidFundamentals2023Theme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        File(cacheDir, "audio.mp3").also {
                            recorder.startRecorder(it)
                            audioFile = it
                        }
                    }) {
                        Text(text = "Start Recording")
                    }

                    Button(onClick = {
                        recorder.stopRecorder()
                    }) {
                        Text(text = "Stop Recording")
                    }

                    Button(onClick = {
                        player.playFile(audioFile ?: return@Button)
                    }) {
                        Text(text = "Play")
                    }

                    Button(onClick = {
                        player.stop()
                    }) {
                        Text(text = "Stop Playing ")
                    }
                }
            }
        }
    }
}
