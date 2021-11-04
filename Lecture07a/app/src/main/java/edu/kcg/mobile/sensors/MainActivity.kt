package edu.kcg.mobile.sensors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        println("All sensors of TYPE_ACCELEROMETER:")
        sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).forEach { println(it) }
        println()
        println("All sensors:")
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach { println(it) }
        println()

//        accelerometer.maximumRange
//        accelerometer.minDelay
//        accelerometer.name
//        accelerometer.power
//        accelerometer.resolution

        findViewById<Button>(R.id.vibrate_button).setOnClickListener {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//            vibrator.vibrate(VibrationEffect.createOneShot(1000, 10))
//            vibrator.vibrate(VibrationEffect.createOneShot(1000, 255))
            val pattern = longArrayOf(500, 1000, 1000, 2000)
            // start after 0.5s, vibrate for 1s, pause for 1s, vibrate for 2s, ...
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
        }
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
                val result = event.values.map { it.toString() }.reduce { a, b -> "$a, $b" }
                findViewById<TextView>(R.id.value).text = result
            }
            Sensor.TYPE_LIGHT -> {
                if (applyLight) {
                    event.values[0].coerceAtMost(150F).div(150F).let { setBackgroundColor(it) }
                }
            }
            Sensor.TYPE_GRAVITY -> {
                applyGravity(event.values[0], event.values[1]) // Z value is not necessary
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        print("Accuracy for the sensor ${sensor.name} has changed to ")
        when (accuracy) {
            SensorManager.SENSOR_STATUS_ACCURACY_LOW -> println("SENSOR_STATUS_ACCURACY_LOW")
            SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM -> println("SENSOR_STATUS_ACCURACY_MEDIUM")
            SensorManager.SENSOR_STATUS_ACCURACY_HIGH -> println("SENSOR_STATUS_ACCURACY_HIGH")
            SensorManager.SENSOR_STATUS_UNRELIABLE -> println("SENSOR_STATUS_UNRELIABLE")
        }
    }

    private fun applyGravity(x: Float, y: Float) {
        val element = findViewById<RadioButton>(R.id.radio_button)
        if (x < -1 || x > 1) {
            element.x -= x
        }
        if (y < -1 || y > 1) {
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

    fun onSwitchChange(view: View) {
        applyLight = (view as SwitchMaterial).isChecked
        if (!applyLight) {
            // reset the background to white color
            setBackgroundColor(1F)
        }
    }

    private fun setBackgroundColor(value: Float) {
        val rgb = Color.rgb(value, value, value)
        findViewById<ConstraintLayout>(R.id.layout).setBackgroundColor(rgb)
    }

}