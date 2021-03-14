package edu.kcg.mobile.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.FileNotFoundException
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var startRecordBtn: Button
    private lateinit var stopRecordBtn: Button
    private lateinit var playBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var mediaRecorder: MediaRecorder
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioFilename: String
    private lateinit var imageView: ImageView

    private val cameraPermissionCode = 200
    private val audioPermissionCode = 201

    private val getImageFromCameraRequestCode = 500
    private val loadAlbumImageRequestCode = 501

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.take_picture).setOnClickListener {
            if (requestCameraPermission()) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, getImageFromCameraRequestCode)
            }
        }
        findViewById<Button>(R.id.read_picture).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, loadAlbumImageRequestCode)
        }

        startRecordBtn = findViewById(R.id.start_record)
        stopRecordBtn = findViewById(R.id.stop_record)
        playBtn = findViewById(R.id.play)
        stopBtn = findViewById(R.id.stop)

        stopRecordBtn.isEnabled = false
        playBtn.isEnabled = false
        stopBtn.isEnabled = false

        audioFilename = "${externalCacheDir?.absolutePath}/AudioRecording.3gp"
        imageView = findViewById(R.id.image_view)

        startRecordBtn.setOnClickListener {
            if (requestAudioPermission()) {
                startAudioRecording()
            }
        }
        stopRecordBtn.setOnClickListener { stopAudioRecording() }
        playBtn.setOnClickListener { startAudioPlaying() }
        stopBtn.setOnClickListener { stopAudioPlaying() }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == getImageFromCameraRequestCode && resultCode == RESULT_OK) {
            val photo = data?.extras?.let { it["data"] } as Bitmap?
            photo?.let { imageView.setImageBitmap(it) }
        } else if (requestCode == loadAlbumImageRequestCode && resultCode == RESULT_OK) {
            try {
                data?.data?.let {
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it))
                    imageView.setImageBitmap(bitmap)
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            cameraPermissionCode -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, getImageFromCameraRequestCode)
                } else {
                    Toast.makeText(applicationContext, "Error when granting camera permission", Toast.LENGTH_LONG).show()
                }
            }
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
    private fun requestCameraPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraPermissionCode)
            return false
        }
        return true
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

}