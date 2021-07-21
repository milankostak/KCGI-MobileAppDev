package edu.kcg.mobile.canvas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.kcg.mobile.canvas.canvas.CanvasActivity
import edu.kcg.mobile.canvas.opengl.OpenGLES20Activity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.go_to_canvas).setOnClickListener {
            startActivity(Intent(this, CanvasActivity::class.java))
        }

        findViewById<Button>(R.id.go_to_opengl).setOnClickListener {
            startActivity(Intent(this, OpenGLES20Activity::class.java))
        }
    }

}
