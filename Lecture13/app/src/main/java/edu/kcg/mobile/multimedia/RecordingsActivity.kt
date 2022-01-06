package edu.kcg.mobile.multimedia

import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException

class RecordingsActivity : AppCompatActivity() {

    private lateinit var btStartRecording: Button
    private lateinit var btStopRecording: Button
    private lateinit var btPlay: Button
    private lateinit var btStop: Button

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioFilename: String

    private val audioPermissionCode = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordings)

        btStartRecording = findViewById(R.id.start_record)
        btStopRecording = findViewById(R.id.stop_record)
        btPlay = findViewById(R.id.play)
        btStop = findViewById(R.id.stop)

        btStopRecording.isEnabled = false
        btPlay.isEnabled = false
        btStop.isEnabled = false

        audioFilename = "${externalCacheDir?.absolutePath}/AudioRecording.3gp"

        btStartRecording.setOnClickListener {
            if (audioPermissionGranted()) {
                startAudioRecording()
            }
        }
        btStopRecording.setOnClickListener { stopAudioRecording() }
        btPlay.setOnClickListener { startAudioPlaying() }
        btStop.setOnClickListener { stopAudioPlaying() }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == audioPermissionCode) {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                startAudioRecording()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Error when granting audio permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun audioPermissionGranted(): Boolean {
        val audioPermission = ActivityCompat.checkSelfPermission(this, RECORD_AUDIO)
        val storagePermission = ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE)
        if (audioPermission != PackageManager.PERMISSION_GRANTED || storagePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(RECORD_AUDIO, WRITE_EXTERNAL_STORAGE), audioPermissionCode
            )
            return false // we cannot continue now, we need to wait for user to confirm (or deny) the permission
        }
        return true // we can continue when the access was already granted in the past
    }

    @SuppressLint("NewApi")
    private fun startAudioRecording() {
        btStartRecording.isEnabled = false
        btStopRecording.isEnabled = true
        btPlay.isEnabled = false
        btStop.isEnabled = false

        mediaRecorder = MediaRecorder(applicationContext)
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile(audioFilename)
        try {
            mediaRecorder.prepare()
            mediaRecorder.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopAudioRecording() {
        btStartRecording.isEnabled = true
        btStopRecording.isEnabled = false
        btPlay.isEnabled = true
        btStop.isEnabled = false

        mediaRecorder.stop()
        mediaRecorder.release()
    }

    private fun startAudioPlaying() {
        btStartRecording.isEnabled = true
        btStopRecording.isEnabled = false
        btPlay.isEnabled = false
        btStop.isEnabled = true

        mediaPlayer = MediaPlayer()
        try {
            mediaPlayer.setDataSource(audioFilename)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stopAudioPlaying() {
        btStartRecording.isEnabled = true
        btStopRecording.isEnabled = false
        btPlay.isEnabled = true
        btStop.isEnabled = false

        mediaPlayer.stop()
        mediaPlayer.release()
    }

}