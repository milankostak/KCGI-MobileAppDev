package edu.kcg.mobile.multimedia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.go_to_recordings).setOnClickListener {
            startActivity(Intent(this, RecordingsActivity::class.java))
        }

        findViewById<Button>(R.id.go_to_videos).setOnClickListener {
            startActivity(Intent(this, VideosActivity::class.java))
        }

        findViewById<Button>(R.id.go_to_youtube_videos).setOnClickListener {
            startActivity(Intent(this, YoutubeVideosActivity::class.java))
        }
    }

}