package edu.kcg.mobile.multimedia

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class YoutubeVideosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_videos)

        findViewById<Button>(R.id.bt_set_video).setOnClickListener {
            val videoId = findViewById<EditText>(R.id.et_video_id).text.toString()
            val strippedVideoId = HtmlCompat.fromHtml(videoId, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
            setupVideo(strippedVideoId, R.id.web_view_1)

//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("https://www.youtube.com/watch?v=P_q3BdrFsLI")
//            )
//            startActivity(intent)
        }

        setupVideo("u_f90pXw5sQ", R.id.web_view_1)
        setupVideo("zCLOJ9j1k2Y", R.id.web_view_2)
    }

    private fun setupVideo(videoId: String, webViewId: Int) {
        val videoUrl =
            "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" frameborder=\"0\" allowfullscreen></iframe>"

        val videoWebView = findViewById<WebView>(webViewId)
        videoWebView.settings.javaScriptEnabled = true
        videoWebView.webChromeClient = WebChromeClient()
        videoWebView.loadData(videoUrl, "text/html", Charsets.UTF_8.displayName())
    }

}