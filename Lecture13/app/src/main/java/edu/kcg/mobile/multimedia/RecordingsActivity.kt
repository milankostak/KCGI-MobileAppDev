package edu.kcg.mobile.multimedia

import android.Manifest
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

    private lateinit var startRecordBtn: Button
    private lateinit var stopRecordBtn: Button
    private lateinit var playBtn: Button
    private lateinit var stopBtn: Button

    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioFilename: String

    private val audioPermissionCode = 201

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recordings)

        startRecordBtn = findViewById(R.id.start_record)
        stopRecordBtn = findViewById(R.id.stop_record)
        playBtn = findViewById(R.id.play)
        stopBtn = findViewById(R.id.stop)

        stopRecordBtn.isEnabled = false
        playBtn.isEnabled = false
        stopBtn.isEnabled = false

        audioFilename = "${externalCacheDir?.absolutePath}/AudioRecording.3gp"

        startRecordBtn.setOnClickListener {
            if (requestAudioPermission()) {
                startAudioRecording()
            }
        }
        stopRecordBtn.setOnClickListener { stopAudioRecording() }
        playBtn.setOnClickListener { startAudioPlaying() }
        stopBtn.setOnClickListener { stopAudioPlaying() }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            audioPermissionCode -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
    }

    private fun requestAudioPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ), audioPermissionCode)
            return false // we cannot continue now, we need to wait for user to confirm (or deny) the permission
        }
        return true // we can continue when the access was already granted in the past
    }

    private fun startAudioRecording() {
        startRecordBtn.isEnabled = false
        stopRecordBtn.isEnabled = true
        playBtn.isEnabled = false
        stopBtn.isEnabled = false

        mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile(audioFilename)
        try {
            mediaRecorder.prepare()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaRecorder.start()
    }

    private fun stopAudioRecording() {
        startRecordBtn.isEnabled = true
        stopRecordBtn.isEnabled = false
        playBtn.isEnabled = true
        stopBtn.isEnabled = false
        mediaRecorder.stop()
        mediaRecorder.release()
    }

    private fun startAudioPlaying() {
        startRecordBtn.isEnabled = true
        stopRecordBtn.isEnabled = false
        playBtn.isEnabled = false
        stopBtn.isEnabled = true
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
        mediaPlayer.release()
        stopRecordBtn.isEnabled = false
        startRecordBtn.isEnabled = true
        playBtn.isEnabled = true
        stopBtn.isEnabled = false
    }

}