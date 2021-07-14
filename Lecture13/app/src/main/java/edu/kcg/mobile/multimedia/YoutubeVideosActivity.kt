package edu.kcg.mobile.multimedia

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class YoutubeVideosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_videos)

        findViewById<Button>(R.id.bt_set_video).setOnClickListener {
            val videoId = findViewById<EditText>(R.id.et_video_id).text.toString()
            setupVideo(videoId, R.id.web_view_1)
        }

        setupVideo("u_f90pXw5sQ", R.id.web_view_1)
        setupVideo("zCLOJ9j1k2Y", R.id.web_view_2)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupVideo(videoId: String, webViewId: Int) {
        val videoUrl =
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen></iframe>"

        val videoWeb = findViewById<WebView>(webViewId)
        videoWeb.settings.javaScriptEnabled = true
        videoWeb.webChromeClient = WebChromeClient()
        videoWeb.loadData(videoUrl, "text/html", "utf-8")
    }

}