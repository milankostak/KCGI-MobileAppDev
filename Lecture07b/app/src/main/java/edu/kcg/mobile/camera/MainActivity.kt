package edu.kcg.mobile.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {

    private lateinit var imageFromCameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var imageView: ImageView
    private val cameraPermissionCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageFromCameraLauncher = getImageFromCameraLauncher()
        findViewById<Button>(R.id.take_picture).setOnClickListener {
            if (requestCameraPermission()) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                imageFromCameraLauncher.launch(cameraIntent)
            }
        }

        val loadAlbumImageLauncher = getLoadAlbumImageLauncher()
        findViewById<Button>(R.id.read_picture).setOnClickListener {
            val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            loadAlbumImageLauncher.launch(albumIntent)
        }

        imageView = findViewById(R.id.image_view)
    }

    private fun getImageFromCameraLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val photo = it?.data?.extras?.let { bundle -> bundle["data"] } as Bitmap?
            photo?.let { bitmap -> imageView.setImageBitmap(bitmap) }
        }
    }

    private fun getLoadAlbumImageLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                it?.data?.data?.let { uri ->
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
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
                    imageFromCameraLauncher.launch(cameraIntent)
                } else {
                    Toast.makeText(applicationContext, "Error when granting camera permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun requestCameraPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), cameraPermissionCode)
            return false
            // cannot continue now, we need to wait for user to confirm (or deny) the permission request
        }
        return true
        // we can continue when the access was already granted in the past
    }

}