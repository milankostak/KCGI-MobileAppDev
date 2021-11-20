package edu.kcg.mobile.canvas.opengl

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class OpenGLES20Activity : Activity() {

    private lateinit var glView: GLSurfaceView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The view layout is not defined as xml file
        glView = MyGLSurfaceView(this)
        setContentView(glView)
    }

}
