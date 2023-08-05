package com.vitorthemyth.audiorecorder2.playback

import java.io.File

interface AudioPlayer {

    fun playFile(file:File)
    fun stop()
}