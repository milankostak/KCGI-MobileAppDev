package edu.kcg.mobile.sensors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var light: Sensor
    private lateinit var gravity: Sensor

    private var paused = true
    private var applyLight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_NORMAL)
        paused = false
    }

    override fun onPause() {
        super.onPause()
        paused = true
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null || paused) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                findViewById<TextView>(R.id.value).text = event.values[0].toString()
            }
            Sensor.TYPE_LIGHT -> {
                if (applyLight) {
                    event.values[0].coerceAtMost(150F).div(150F).let { setBackgroundColor(it) }
                }
            }
            Sensor.TYPE_GRAVITY -> {
                applyGravity(event.values[0], event.values[1])//, event.values[2]) // Z not necessary
            }
        }
    }

    private fun applyGravity(x: Float, y: Float) {
        val element = findViewById<RadioButton>(R.id.radio_button)
        if (x < 1 || x > 1) {
            element.x -= x
        }
        if (y < 1 || y > 1) {
            element.y += y
        }

        if (element.x < 0) element.x = 0F
        if (element.y < 0) element.y = 0F

        val layout = findViewById<ConstraintLayout>(R.id.layout)
        val maxX = layout.width - element.width
        if (element.x > maxX) element.x = maxX.toFloat()

        val maxY = layout.height - element.height
        if (element.y > maxY) element.y = maxY.toFloat()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println(accuracy)
    }

    fun onSwitchChange(view: View) {
        applyLight = findViewById<SwitchMaterial>(R.id.switch_light).isChecked
        if (!applyLight) {
            setBackgroundColor(1F)
        }
    }

    private fun setBackgroundColor(value: Float) {
        val rgb = Color.rgb(value, value, value)
        findViewById<ConstraintLayout>(R.id.layout).setBackgroundColor(rgb)
    }

}