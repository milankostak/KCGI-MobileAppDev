package edu.kcg.mobile.multimedia

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.VideoView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.FileNotFoundException

class VideosActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos)

        videoView = findViewById(R.id.video_view)

        // 1. loading remote video
//        // the video is 170 MB for 47 seconds so buffering may take some time
//        val url = "https://owncloud.cesnet.cz/index.php/s/qeiHijmj8q82IeW/download"
//        videoView.setVideoPath(url)
//        videoView.start()

        // 2. loading local video
        val loadAlbumImageLauncher = getLoadAlbumImageLauncher()
        val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        loadAlbumImageLauncher.launch(albumIntent)
    }

    private fun getLoadAlbumImageLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it?.data?.data?.let { uri ->
                videoView.setVideoURI(uri)
                videoView.start()
            }
        }
    }

}