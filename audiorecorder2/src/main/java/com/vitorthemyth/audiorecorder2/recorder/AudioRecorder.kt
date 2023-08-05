package com.vitorthemyth.audiorecorder2.recorder

import java.io.File

interface AudioRecorder {
    fun startRecorder(outputFile: File)
    fun stopRecorder()
}