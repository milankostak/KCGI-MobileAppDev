package edu.kcg.mobile.maps

import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var etAddress: EditText
    private lateinit var geocoder: Geocoder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        findViewById<Button>(R.id.button).setOnClickListener {
            searchAddress()
        }
        etAddress = findViewById(R.id.et_address)
        geocoder = Geocoder(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnMapLongClickListener {
            val addressesList = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val locationName = addressesList.firstOrNull()?.getAddressLine(0) ?: "Unknown location"
            mMap.addMarker(MarkerOptions().position(it).title(locationName))
            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }
    }

    private fun searchAddress() {
        val location = etAddress.text.toString()
        if (location.isNotBlank()) {
            val addressesList = geocoder.getFromLocationName(location, 1)
            addressesList.firstOrNull()?.let {
                val latLng = LatLng(it.latitude, it.longitude)
//                println(it.countryName)
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }
    }
}