package edu.kcg.mobile.gps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var loadingIcon: ProgressBar
    private lateinit var btRequestPosition: Button

    private lateinit var tvTime: TextView
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvAccuracy: TextView
    private lateinit var tvAltitude: TextView
    private lateinit var tvVerticalAccuracy: TextView
    private lateinit var tvSpeed: TextView
    private lateinit var tvSpeedAccuracy: TextView
    private lateinit var tvBearing: TextView
    private lateinit var tvBearingAccuracy: TextView
    private lateinit var tvProvider: TextView
    private lateinit var tvSatellites: TextView

    private lateinit var locationManager: LocationManager
    private lateinit var listener: LocationListener

    private val dateTimeFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    private val coordinatesFormat = DecimalFormat("0.00000000°")
    private val altitudeFormat = DecimalFormat("0.0 m")
    private val accuracyFormat = DecimalFormat("0 m")
    private val speedFormat = DecimalFormat("0.00 km/h")
    private val degreesFormat = DecimalFormat("0°")
    private val plainIntegerFormat = DecimalFormat("0")

    private var requestInProgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingIcon = findViewById(R.id.loading_icon)
        tvTime = findViewById(R.id.tv_time)
        tvLatitude = findViewById(R.id.tv_latitude)
        tvLongitude = findViewById(R.id.tv_longitude)
        tvAccuracy = findViewById(R.id.tv_accuracy)
        tvAltitude = findViewById(R.id.tv_altitude)
        tvVerticalAccuracy = findViewById(R.id.tv_vertical_accuracy)
        tvSpeed = findViewById(R.id.tv_speed)
        tvSpeedAccuracy = findViewById(R.id.tv_speed_accuracy)
        tvBearing = findViewById(R.id.tv_bearing)
        tvBearingAccuracy = findViewById(R.id.tv_bearing_accuracy)
        tvProvider = findViewById(R.id.tv_provider)
        tvSatellites = findViewById(R.id.tv_satellites)

        btRequestPosition = findViewById(R.id.bt_request_position)
        btRequestPosition.setOnClickListener { updatePosition() }

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        listener = LocationListener { setLocation(it) }
    }

    override fun onRequestPermissionsResult(code: Int, permissions: Array<String>, res: IntArray) {
        when (code) {
            PERMISSION_REQUEST_LOCATION -> {
                if (res.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
                    updatePosition()
                }
            }
            else -> super.onRequestPermissionsResult(code, permissions, res)
        }
    }

    private fun updatePosition() {
        if (!requestInProgress) {
            // first check for permissions
            val permissionFineLocation =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            val permissionCoarseLocation =
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            if (permissionFineLocation != PackageManager.PERMISSION_GRANTED && permissionCoarseLocation != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS, PERMISSION_REQUEST_LOCATION)
            } else {
                requestInProgress = true
                loadingIcon.visibility = View.VISIBLE
                btRequestPosition.text = resources.getText(R.string.stop_request)
//                locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null)
//                locationManager.getCurrentLocation(LocationManager.GPS_PROVIDER, CancellationSignal(),
//                    {},
//                    { setLocation(it) })
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000,
                    0f,
                    listener
                )
            }
        } else {
            locationManager.removeUpdates(listener)
            requestInProgress = false
            loadingIcon.visibility = View.GONE
            btRequestPosition.text = resources.getText(R.string.request_location)
        }
    }

    private fun setLocation(location: Location) {
        tvTime.text = dateTimeFormat.format(Date())

        tvLatitude.text = coordinatesFormat.format(location.latitude)
        tvLongitude.text = coordinatesFormat.format(location.longitude)

        if (location.hasAccuracy()) {
            tvAccuracy.text = accuracyFormat.format(location.accuracy)
        } else {
            tvAccuracy.text = "-"
        }

        if (location.hasAltitude()) {
            tvAltitude.text = altitudeFormat.format(location.altitude)
        } else {
            tvAltitude.text = "-"
        }

        if (location.hasVerticalAccuracy()) {
            tvVerticalAccuracy.text = accuracyFormat.format(location.verticalAccuracyMeters)
        } else {
            tvVerticalAccuracy.text = "-"
        }

        if (location.hasSpeed()) {
            tvSpeed.text = speedFormat.format(location.speed)
        } else {
            tvSpeed.text = "-"
        }
        if (location.hasSpeedAccuracy()) {
            tvSpeedAccuracy.text = speedFormat.format(location.speedAccuracyMetersPerSecond)
        } else {
            tvSpeedAccuracy.text = "-"
        }

        if (location.hasBearing()) {
            tvBearing.text = degreesFormat.format(location.bearing)
        } else {
            tvBearing.text = "-"
        }
        if (location.hasBearingAccuracy()) {
            tvBearingAccuracy.text = degreesFormat.format(location.bearingAccuracyDegrees)
        } else {
            tvBearingAccuracy.text = "-"
        }

        tvProvider.text = location.provider
        tvSatellites.text = "-"
        if (location.extras.containsKey("satellites")) {
            val satellitesObject = location.extras.get("satellites")
            if (satellitesObject is Int) {
                tvSatellites.text = plainIntegerFormat.format(satellitesObject)
            }
        }
    }

    companion object {
        const val PERMISSION_REQUEST_LOCATION = 10
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

}